package com.lasindu.clothfy_store.service;

import com.google.auth.Credentials;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.storage.BlobId;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;
import com.lasindu.clothfy_store.repository.ImageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

/**
 * @author Lasindu Anjana
 * @email lasindua@gmail.com
 * @createdDate 8/29/23
 **/

@Service
@RequiredArgsConstructor
public class ImageService {
    private static final String DOWNLOAD_URL = "https://firebasestorage.googleapis.com/v0/b/clothify-store.appspot.com/o/%s?alt=media";
    private final ImageRepository imageRepository;

    // TODO: change to use path prom application.properties
    private final String storagePath= "/home/lasindu/Desktop/Java/clothfy_store/src/main/java/com/lasindu/clothfy_store/uploads/images/";
    private final Path fileStoragePath = Paths.get(storagePath);


    public ResponseEntity<String> upload(MultipartFile multipartFile) {

        try {
            String fileName = multipartFile.getOriginalFilename();                        // to get original file name
            fileName = UUID.randomUUID().toString().concat(this.getExtension(fileName));  // to generated random string values for file name.

            File file = this.convertToFile(multipartFile, fileName);                      // to convert multipartFile to File
            String TEMP_URL = this.uploadFile(file, fileName);                                   // to get uploaded file link
            file.delete();                                                                // to delete the copy of uploaded file stored in the project folder
            return new ResponseEntity<String>(TEMP_URL ,HttpStatus.CREATED);                     // Your customized response
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<String>("Unsuccessfully Uploaded!", HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    private String uploadFile(File file, String fileName) throws IOException {
        BlobId blobId = BlobId.of("clothify-store.appspot.com", fileName);
        BlobInfo blobInfo = BlobInfo.newBuilder(blobId).setContentType("media").build();
        Credentials credentials = GoogleCredentials.fromStream(new FileInputStream("/home/lasindu/Desktop/Java/clothfy_store/src/main/resources/firebase-adminsdk.json"));
        Storage storage = StorageOptions.newBuilder().setCredentials(credentials).build().getService();
        storage.create(blobInfo, Files.readAllBytes(file.toPath()));
        return String.format(DOWNLOAD_URL, URLEncoder.encode(fileName, StandardCharsets.UTF_8));
    }

    // convert MultipartFile to File
    private File convertToFile(MultipartFile multipartFile, String fileName) throws IOException {
        File tempFile = new File(fileName);
        try (FileOutputStream fos = new FileOutputStream(tempFile)) {
            fos.write(multipartFile.getBytes());
            fos.close();
        }
        return tempFile;
    }

    //get file extension
    private String getExtension(String fileName) {
        return fileName.substring(fileName.lastIndexOf("."));
    }
    public boolean uploadImage(MultipartFile file) throws IOException {
    /*
     * This function only save the image to server
     * Use the exact same name in the add product route
     * to access file use getImage function
     */

        // TODO: upload images to storage bucket and get url
        if (!Files.exists(fileStoragePath)) {
            Files.createDirectories(fileStoragePath);
        }

        try (InputStream inputStream = file.getInputStream();
            OutputStream outputStream = new FileOutputStream(new File(storagePath + file.getOriginalFilename()))) {
            byte[] buffer = new byte[1024];
            int bytesRead;

            while ((bytesRead = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
            }

            return true;
        }
    }

    public ResponseEntity<InputStreamResource> getImage(Long id) throws IOException {
        var image = imageRepository.findById(id);
        if (image.isPresent()) {
            File file = new File(storagePath + image.get().getFilename());
            InputStreamResource inputStreamResource = new InputStreamResource(new FileInputStream(file));

            // Set the headers for the response
            HttpHeaders headers = new HttpHeaders();
            headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + file.getName());

//            filePath = fileStoragePath.resolve(image.get().getFilename());
            return ResponseEntity.ok()
                    .headers(headers)
                    .contentType(MediaType.APPLICATION_OCTET_STREAM)
                    .body(inputStreamResource);
        }
        return null;
    }
}
