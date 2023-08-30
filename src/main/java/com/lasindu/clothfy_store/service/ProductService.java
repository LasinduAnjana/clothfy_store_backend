package com.lasindu.clothfy_store.service;

import com.lasindu.clothfy_store.dto.request.AddProductReqDTO;
import com.lasindu.clothfy_store.dto.request.ImageDTO;
import com.lasindu.clothfy_store.dto.request.SellProductReqDTO;
import com.lasindu.clothfy_store.dto.response.MessageResDTO;
import com.lasindu.clothfy_store.entity.Image;
import com.lasindu.clothfy_store.entity.Product;
import com.lasindu.clothfy_store.repository.ImageRepository;
import com.lasindu.clothfy_store.repository.ProductRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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


        List<ImageDTO> images = request.getImages();
        images.forEach(imageDTO -> {
            if (productRepository.findById(imageDTO.getProductId()).isPresent())
                imageRepository.save(Image.builder()
                    .product(productRepository.findById(imageDTO.getProductId()).get())
                    .placement(imageDTO.getPlacement())
                    .filename(imageDTO.getFileName())
                    .build());
        });

        return productRepository.findById(product.getId());

    }

    public List<Product> getAllProducts() {
        return productRepository.findAll();
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
