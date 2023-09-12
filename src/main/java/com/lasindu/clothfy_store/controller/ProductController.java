package com.lasindu.clothfy_store.controller;

import com.lasindu.clothfy_store.dto.request.AddProductReqDTO;
import com.lasindu.clothfy_store.dto.request.SellProductReqDTO;
import com.lasindu.clothfy_store.dto.response.MessageResDTO;
import com.lasindu.clothfy_store.dto.response.ProductDTO;
import com.lasindu.clothfy_store.entity.CartItem;
import com.lasindu.clothfy_store.entity.Product;
import com.lasindu.clothfy_store.entity.ProductCategory;
import com.lasindu.clothfy_store.entity.ProductType;
import com.lasindu.clothfy_store.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

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
    public ResponseEntity<List<ProductDTO>> getAllProducts() {
        return productService.getAllProducts();
    }

    @GetMapping("/public/product/new")
    public ResponseEntity<List<ProductDTO>> getNewProduct() {
        return productService.getNewProducts();
    }

    @GetMapping("/public/product/{productId}")
    public ResponseEntity<?> getProductById(@PathVariable UUID productId) {
        return productService.getProductById(productId);
    }

    @PostMapping("/admin/product")
    public ResponseEntity<ProductDTO> addProduct(@RequestBody AddProductReqDTO request) {
        return productService.addProduct(request);
    }

    @PutMapping("/user/product/buy/{id}")
    public ResponseEntity<MessageResDTO> sellProduct(@PathVariable UUID id, @RequestBody SellProductReqDTO request) {
        return productService.sellProduct(id, request);
    }

    @PostMapping("/user/product/cart/{id}")
    public ResponseEntity<?> addToCartProduct(@PathVariable UUID id, @RequestBody int quantity) {
        return productService.addToCartProduct(id, quantity);
    }

    @GetMapping("/public/product/filter-by-type")
    public ResponseEntity<List<ProductDTO>> getProductByType(@RequestParam ProductType type) {
        return productService.getProductByProductType(type);
    }

    @GetMapping("/public/product/filter-by-category")
    public ResponseEntity<List<ProductDTO>> getProductByCategory(@RequestParam ProductCategory category) {
        return productService.getProductByProductCategory(category);
    }

}
