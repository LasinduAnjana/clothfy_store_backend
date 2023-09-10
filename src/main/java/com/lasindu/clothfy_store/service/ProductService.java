package com.lasindu.clothfy_store.service;

import com.lasindu.clothfy_store.dto.request.AddProductReqDTO;
import com.lasindu.clothfy_store.dto.response.MessageResDTO;
import com.lasindu.clothfy_store.dto.response.ProductDTO;
import com.lasindu.clothfy_store.entity.*;
import com.lasindu.clothfy_store.repository.CartItemRepository;
import com.lasindu.clothfy_store.repository.CartRepository;
import com.lasindu.clothfy_store.repository.ImageRepository;
import com.lasindu.clothfy_store.repository.ProductRepository;
import com.lasindu.clothfy_store.util.UserUtil;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    private final CartItemRepository cartItemRepository;
    private final CartRepository cartRepository;

    private final UserUtil userUtil;

    public ResponseEntity<ProductDTO> addProduct(AddProductReqDTO request) {
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


        List<String> imageLinks = request.getImageLinks();

        for (int i = 0; i < imageLinks.size(); i++) {
            imageRepository.save(
                    Image.builder()
                    .product(product)
                    .link(imageLinks.get(i))
                    .placement(i)
                    .build()
            );
        }
        Optional<List<Image>> images = imageRepository.findAllByProductOrderByPlacement(product);
        List<String> links = new ArrayList<String>();

        images.ifPresent(imageList -> imageList.forEach(link -> {
            links.add(link.getLink());
        }));

        ProductDTO response = ProductDTO.builder()
                .title(product.getTitle())
                .type(product.getType())
                .size(product.getSize())
                .price(product.getPrice())
                .material(product.getMaterial())
                .weight(product.getWeight())
                .description(product.getDescription())
                .quantity(product.getQuantity())
                .imageLinks(links)
                .build();

        return new ResponseEntity<>(response, HttpStatus.CREATED);

    }

    public ResponseEntity<List<ProductDTO>> getAllProducts() {
        Optional<List<Product>> productList = Optional.of(productRepository.findAll());
        return getListResponseEntity(productList);
    }

    public ResponseEntity<?> getProductById(Long id) {
        Optional<Product> product = productRepository.findById(id);
        if (product.isPresent())
            return new ResponseEntity<>(product.get(), HttpStatus.OK);
        return new ResponseEntity<>(new MessageResDTO("product not found"), HttpStatus.NOT_FOUND);
    }

    @Transactional
    public ResponseEntity<MessageResDTO> sellProduct(Long id, int quantity) {
        Optional<Product> product = productRepository.findById(id);
        if (product.isPresent()) {
            productRepository.updateQuantityById(product.get().getQuantity() - quantity, id);
            return new ResponseEntity<>(new MessageResDTO("product quantity update successfully"), HttpStatus.OK);
        }
        return new ResponseEntity<>(new MessageResDTO("product quantity update failed"), HttpStatus.BAD_REQUEST);

    }

    public ResponseEntity<?> addToCartProduct(Long id, int quantity) {
        Optional<Product> product = productRepository.findById(id);
        Optional<User> user = userUtil.getUserDetails();

        if (product.isPresent() && user.isPresent() && cartRepository.findCartByUserId(user.get().getId()).isPresent()){
            return new ResponseEntity<>(cartItemRepository.save(
                CartItem
                    .builder()
                    .cart(cartRepository.findCartByUserId(user.get().getId()).get())
                    .product(product.get())
                    .quantity(quantity)
                    .build()
            ), HttpStatus.OK);
        }
        return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
    }

    public ResponseEntity<List<ProductDTO>> getNewProducts() {
        Optional<List<Product>> productList = productRepository.findTop10();
        return getListResponseEntity(productList);
    }

    public ResponseEntity<List<ProductDTO>> getProductByProductType(ProductType type) {
        Optional<List<Product>> productList = productRepository.findAllByTypeOrderById(type);
        return getListResponseEntity(productList);
    }

    public ResponseEntity<List<ProductDTO>> getProductByProductCategory(ProductCategory category) {
        Optional<List<Product>> productList = productRepository.findAllByCategoryOrderById(category);
        return getListResponseEntity(productList);
    }

    private ResponseEntity<List<ProductDTO>> getListResponseEntity(Optional<List<Product>> productList) {
        List<ProductDTO> responseList = new ArrayList<>();
        if (productList.isPresent()) {
            productList.get().forEach(product -> {
                Optional<List<Image>> images = imageRepository.findAllByProductOrderByPlacement(product);
                List<String> links = new ArrayList<>();

                if (images.isPresent()) {
                    images.get().forEach(link -> {
                        links.add(link.getLink());
                    });

                    responseList.add(ProductDTO.builder()
                            .title(product.getTitle())
                            .quantity(product.getQuantity())
                            .description(product.getDescription())
                            .weight(product.getWeight())
                            .material(product.getMaterial())
                            .price(product.getPrice())
                            .size(product.getSize())
                            .type(product.getType())
                            .imageLinks(links)
                            .build());
                }
            });
            return new ResponseEntity<>(responseList, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
        }
    }
}
