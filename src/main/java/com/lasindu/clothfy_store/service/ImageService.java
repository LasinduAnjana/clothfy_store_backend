package com.lasindu.clothfy_store.service;

import com.lasindu.clothfy_store.repository.ImageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * @author Lasindu Anjana
 * @email lasindua@gmail.com
 * @createdDate 8/29/23
 **/

@Service
@RequiredArgsConstructor
public class ImageService {
    private final ImageRepository imageRepository;

    // TODO: change to use path prom application.properties
    private final String storagePath= "/home/lasindu/Desktop/Java/clothfy_store/src/main/java/com/lasindu/clothfy_store/uploads/images/";
    private final Path fileStoragePath = Paths.get(storagePath);



    public boolean uploadImage(MultipartFile file) throws IOException {
    /*
     * This function only save the image to server
     * Use the exact same name in the add product route
     * to access file use getImage function
     */
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
