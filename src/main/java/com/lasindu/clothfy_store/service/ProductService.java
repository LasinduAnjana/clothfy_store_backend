package com.lasindu.clothfy_store.service;

import com.lasindu.clothfy_store.dto.request.AddProductReqDTO;
import com.lasindu.clothfy_store.dto.request.AddToCartReqDTO;
import com.lasindu.clothfy_store.dto.request.QuantityReqDTO;
import com.lasindu.clothfy_store.dto.request.SellProductReqDTO;
import com.lasindu.clothfy_store.dto.response.MessageResDTO;
import com.lasindu.clothfy_store.dto.response.ProductDTO;
import com.lasindu.clothfy_store.entity.*;
import com.lasindu.clothfy_store.repository.*;
import com.lasindu.clothfy_store.util.UserUtil;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

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
    private final QuantityRepository quantityRepository;

    private final UserUtil userUtil;

    @Transactional
    public ResponseEntity<ProductDTO> addProduct(AddProductReqDTO request) {

        QuantityReqDTO reqQuantity = request.getQuantity();
        Quantity quantity = quantityRepository.save(
                Quantity.builder()
                        .extraSmall(reqQuantity.getExtraSmall())
                        .small(reqQuantity.getSmall())
                        .medium(reqQuantity.getMedium())
                        .large(reqQuantity.getLarge())
                        .extraLarge(reqQuantity.getExtraLarge())
                        .doubleExtraLarge(reqQuantity.getDoubleExtraLarge())
                        .build()
        );

        Product product = productRepository.save(Product.builder()
                .title(request.getTitle())
                .description(request.getDescription())
                .material(request.getMaterial())
                .quantity(quantity)
                .weight(request.getWeight())
                .price(request.getPrice())
                .size(request.getSize())
                .category(request.getCategory())
                .type(request.getType())
                .build());

        quantity.setProduct(product);
        quantityRepository.save(quantity);


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
                .id(product.getId())
                .title(product.getTitle())
                .type(product.getType())
                .size(product.getSize())
                .price(product.getPrice())
                .material(product.getMaterial())
                .category(product.getCategory())
                .weight(product.getWeight())
                .description(product.getDescription())
                .quantity(reqQuantity)
                .imageLinks(links)
                .build();

        return new ResponseEntity<>(response, HttpStatus.CREATED);

    }

    @Transactional
    public ResponseEntity<List<ProductDTO>> getAllProducts() {
        Optional<List<Product>> productList = Optional.of(productRepository.findAll());
        return getListResponseEntity(productList);
    }

    public ResponseEntity<?> getProductById(UUID id) {
        Optional<Product> product = productRepository.findById(id);
        if (product.isPresent())
            return new ResponseEntity<>(product.get(), HttpStatus.OK);
        return new ResponseEntity<>(new MessageResDTO("product not found"), HttpStatus.NOT_FOUND);
    }

    @Transactional
    public ResponseEntity<MessageResDTO> sellProduct(UUID id, SellProductReqDTO request) {

        Optional<Product> product = productRepository.findById(id);
        if (product.isPresent()) {

            Quantity quantity = product.get().getQuantity();
            switch (request.getSize()) {
                case EXTRA_SMALL -> quantity.setExtraSmall(quantity.getExtraSmall() - request.getQuantity());
                case SMALL -> quantity.setSmall(quantity.getSmall() - request.getQuantity());
                case MEDIUM -> quantity.setMedium(quantity.getMedium() - request.getQuantity());
                case LARGE -> quantity.setLarge(quantity.getLarge() - request.getQuantity());
                case EXTRA_LARGE -> quantity.setExtraLarge(quantity.getExtraLarge() - request.getQuantity());
                case DOUBLE_EXTRA_LARGE -> quantity.setDoubleExtraLarge(quantity.getDoubleExtraLarge() - request.getQuantity());
            }

            quantityRepository.save(quantity);

            return new ResponseEntity<>(new MessageResDTO("product quantity update successfully"), HttpStatus.OK);
        }
        return new ResponseEntity<>(new MessageResDTO("product quantity update failed"), HttpStatus.BAD_REQUEST);

    }

    public ResponseEntity<?> addToCartProduct(UUID id, AddToCartReqDTO quantity) {
        Optional<Product> product = productRepository.findById(id);
        Optional<User> user = userUtil.getUserDetails();

        if (product.isPresent() && user.isPresent()){
            return new ResponseEntity<>(cartItemRepository.save(
                CartItem
                    .builder()
                    .cart(user.get().getCart())
                    .product(product.get())
                    .quantity(quantity.getQuantity())
                    .size(quantity.getSize())
                    .build()
            ), HttpStatus.OK);
        }
        return new ResponseEntity<>("product or user not found", HttpStatus.BAD_REQUEST);
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

                    QuantityReqDTO resQuantity = QuantityReqDTO.builder()
                            .extraSmall(product.getQuantity().getExtraSmall())
                            .small(product.getQuantity().getSmall())
                            .medium(product.getQuantity().getMedium())
                            .large(product.getQuantity().getLarge())
                            .extraLarge(product.getQuantity().getExtraLarge())
                            .doubleExtraLarge(product.getQuantity().getDoubleExtraLarge())
                            .build();

                    responseList.add(ProductDTO.builder()
                            .id(product.getId())
                            .title(product.getTitle())
                            .quantity(resQuantity)
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
