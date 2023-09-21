package com.lasindu.clothfy_store.controller;

import com.lasindu.clothfy_store.dto.response.MessageResDTO;
import com.lasindu.clothfy_store.service.ImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * @author Lasindu Anjana
 * @email lasindua@gmail.com
 * @createdDate 8/29/23
 **/

@RestController
@RequestMapping("api/v1")
@RequiredArgsConstructor
//@CrossOrigin(origins = "http://localhost:3000")
public class ImageController {
    private final ImageService imageService;

    @PostMapping("/admin/image")
    public ResponseEntity<String> upload(@RequestParam("multipartFile") MultipartFile multipartFile) {
        return imageService.upload(multipartFile);
    }

//    @GetMapping("/public/image/{id}")
//    public ResponseEntity<?> readImage(@PathVariable long id) {
//        try {
//            var image = imageService.getImage(id);
//            if (image == null) {
//                return new ResponseEntity<MessageResDTO>(new MessageResDTO("image not found"), HttpStatus.NOT_FOUND);
//            } else {
//                return imageService.getImage(id);
//            }
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//    }
}
