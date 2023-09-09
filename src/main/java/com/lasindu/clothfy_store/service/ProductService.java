package com.lasindu.clothfy_store.service;

import com.lasindu.clothfy_store.dto.request.AddProductReqDTO;
import com.lasindu.clothfy_store.dto.request.ImageDTO;
import com.lasindu.clothfy_store.dto.request.SellProductReqDTO;
import com.lasindu.clothfy_store.dto.response.MessageResDTO;
import com.lasindu.clothfy_store.dto.response.ProductDTO;
import com.lasindu.clothfy_store.entity.Image;
import com.lasindu.clothfy_store.entity.Product;
import com.lasindu.clothfy_store.repository.ImageRepository;
import com.lasindu.clothfy_store.repository.ProductRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @author Lasindu Anjana
 * @email lasindua@gmail.com
 * @createdDate 8/28/23
 **/

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;
    private final ImageRepository imageRepository;

    @Transactional
    public Optional<Product> addProduct(AddProductReqDTO request) {
        var product = productRepository.save(Product.builder()
                .title(request.getTitle())
                .description(request.getDescription())
                .material(request.getMaterial())
                .weight(request.getWeight())
                .quantity(request.getQuantity())
                .price(request.getPrice())
                .size(request.getSize())
                .category(request.getCategory())
                .type(request.getType())
                .build());


//        List<String> imageLinks = request.getImageLinks();
//
//        imageLinks.forEach((link) -> {
//                imageRepository.save(Image.builder()
//                    .product(product)
//                    .placement(index)
//                    .filename(imageDTO.getFileName())
//                    .build());
//        });

        return productRepository.findById(product.getId());

    }

    public List<ProductDTO> getAllProducts() {
        List<Product> productList = productRepository.findAll();
        List<ProductDTO> responseList = new ArrayList<ProductDTO>();
        productList.forEach(product -> {
            // TODO: get image links as array and add to response object
            responseList.add(ProductDTO.builder()
                    .title(product.getTitle())
                    .quantity(product.getQuantity())
                    .description(product.getDescription())
                    .weight(product.getWeight())
                    .material(product.getMaterial())
                    .price(product.getPrice())
                    .size(product.getSize())
                    .type(product.getType())
                    .build());
        });
        return responseList;
    }

    public Optional<Product> getProductById(Long id) {
        return productRepository.findById(id);
    }

    @Transactional
    public MessageResDTO sellProduct(Long id, SellProductReqDTO request) {
        Optional<Product> product =productRepository.findById(id);
        if (product.isPresent()) {
            productRepository.updateQuantityById(product.get().getQuantity()- request.getQuantity(), id);
            return new MessageResDTO("product quantity update successfully");
        }
        return new MessageResDTO("product quantity update failed");

    }
}
