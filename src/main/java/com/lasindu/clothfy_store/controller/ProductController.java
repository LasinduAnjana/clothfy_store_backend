package com.lasindu.clothfy_store.controller;

import com.lasindu.clothfy_store.dto.request.AddProductReqDTO;
import com.lasindu.clothfy_store.dto.request.AddToCartReqDTO;
import com.lasindu.clothfy_store.dto.request.SellProductReqDTO;
import com.lasindu.clothfy_store.dto.response.MessageResDTO;
import com.lasindu.clothfy_store.dto.response.ProductResDTO;
import com.lasindu.clothfy_store.entity.ProductCategory;
import com.lasindu.clothfy_store.entity.ProductType;
import com.lasindu.clothfy_store.service.ProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

import lombok.RequiredArgsConstructor;

/**
 * @author Lasindu Anjana
 * @email lasindua@gmail.com
 * @createdDate 8/28/23
 **/

@RestController
@RequestMapping("api/v1")
@RequiredArgsConstructor
@CrossOrigin
public class ProductController {
    private final ProductService productService;

    @GetMapping("/public/product")
    public ResponseEntity<List<ProductResDTO>> getAllProducts() {
        return productService.getAllProducts();
    }

    @GetMapping("/public/product/new")
    public ResponseEntity<List<ProductResDTO>> getNewProduct() {
        return productService.getNewProducts();
    }

    @GetMapping("/public/product/{productId}")
    public ResponseEntity<?> getProductById(@PathVariable UUID productId) {
        return productService.getProductById(productId);
    }

    @PostMapping("/admin/product")
    public ResponseEntity<ProductResDTO> addProduct(@RequestBody AddProductReqDTO request) {
        return productService.addProduct(request);
    }

    @PutMapping("/user/product/buy/{id}")
    public ResponseEntity<MessageResDTO> sellProduct(@PathVariable UUID id, @RequestBody SellProductReqDTO request) {
        return productService.sellProduct(id, request);
    }

    @PostMapping("/user/product/cart/{id}")
    public ResponseEntity<?> addToCartProduct(@PathVariable UUID id, @RequestBody AddToCartReqDTO reqDTO) {
        return productService.addToCartProduct(id, reqDTO);
    }

    @GetMapping("/public/product/filter-by-type")
    public ResponseEntity<List<ProductResDTO>> getProductByType(@RequestParam ProductType type) {
        return productService.getProductByProductType(type);
    }

    @GetMapping("/public/product/filter-by-category")
    public ResponseEntity<List<ProductResDTO>> getProductByCategory(@RequestParam ProductCategory category) {
        return productService.getProductByProductCategory(category);
    }
}
