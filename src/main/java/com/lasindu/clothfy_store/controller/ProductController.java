package com.lasindu.clothfy_store.controller;

import com.lasindu.clothfy_store.dto.request.AddProductReqDTO;
import com.lasindu.clothfy_store.dto.request.SellProductReqDTO;
import com.lasindu.clothfy_store.dto.response.MessageResDTO;
import com.lasindu.clothfy_store.entity.Product;
import com.lasindu.clothfy_store.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * @author Lasindu Anjana
 * @email lasindua@gmail.com
 * @createdDate 8/28/23
 **/

@RestController
@RequestMapping("api/v1/")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;

    @GetMapping("/public/product")
    public ResponseEntity<List<Product>> getAllProducts() {
        return new ResponseEntity<List<Product>>(productService.getAllProducts(), HttpStatus.OK);
    }

    @GetMapping("/public/product/{productId}")
    public ResponseEntity<Optional<Product>> getProductById(@PathVariable Long productId) {
        return new ResponseEntity<Optional<Product>>(productService.getProductById(productId), HttpStatus.OK);
    }

    @PostMapping("/admin/product")
    public ResponseEntity<Optional<Product>> addProduct(@RequestBody AddProductReqDTO request) {
        return new ResponseEntity<Optional<Product>>(productService.addProduct(request), HttpStatus.CREATED);
    }

    @PutMapping("/user/product/buy/{id}")
    public ResponseEntity<MessageResDTO> sellProduct(@PathVariable Long id, @RequestBody SellProductReqDTO request) {
        return new ResponseEntity<MessageResDTO>(productService.sellProduct(id, request), HttpStatus.CREATED);
    }
}
