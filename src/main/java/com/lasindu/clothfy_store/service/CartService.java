package com.lasindu.clothfy_store.service;

import com.lasindu.clothfy_store.dto.response.CartItemResDTO;
import com.lasindu.clothfy_store.dto.response.MessageResDTO;
import com.lasindu.clothfy_store.entity.CartItem;
import com.lasindu.clothfy_store.repository.CartItemRepository;
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
import java.util.UUID;

/**
 * @author Lasindu Anjana
 * @email lasindua@gmail.com
 * @createdDate 9/3/23
 **/

@Service
@RequiredArgsConstructor
public class CartService {
    private final CartItemRepository cartItemRepository;
    private final ProductRepository productRepository;

    private final UserUtil userUtil;

    public ResponseEntity<?> getAllCartItemsByUser(){
        if (userUtil.getUserDetails().isPresent()) {
            // response list
            List<CartItemResDTO> response = new ArrayList<>();
            userUtil.getUserDetails().get().getCart().forEach(cartItem -> {
                response.add(CartItemResDTO.builder()
                        .id(cartItem.getId())
                        .price(cartItem.getProduct().getPrice())
                        .quantity(cartItem.getQuantity())
                        .size(cartItem.getSize())
                        .title(cartItem.getProduct().getTitle())
                        .imageSrc(cartItem.getProduct().getImages().get(0).getLink())
                        .build());
            });


            return new ResponseEntity<>(response, HttpStatus.OK);
        }
        return new ResponseEntity<>("cart not found" , HttpStatus.NOT_FOUND);

    }

    @Transactional
    public MessageResDTO clearCartByUser() {
        if (userUtil.getUserDetails().isPresent()) {
            cartItemRepository.deleteAllByUser(userUtil.getUserDetails().get());
            return new MessageResDTO("cart cleared");
        }
        return new MessageResDTO("cart clear failed");
    }

    @Transactional
    public ResponseEntity<?> removeCartItem(UUID itemId) {
        if(cartItemRepository.findById(itemId).isPresent()) {
            cartItemRepository.deleteById(itemId);
            return new ResponseEntity<>("item removed from cart", HttpStatus.OK);
        }
        return new ResponseEntity<>("item not found in the cart", HttpStatus.NOT_FOUND);
    }

    public boolean confirmSellByCart(UUID cartId) {
        if (userUtil.getUserDetails().isPresent()) {
            Optional<List<CartItem>> itemList = cartItemRepository.findAllByUser(userUtil.getUserDetails().get());
            itemList.ifPresent(cartItems -> cartItems.forEach(cartItem -> {
                productRepository.updateQuantityById(cartItem.getQuantity(), cartItem.getProduct().getId());
            }));
            return true;
        }
        return false;
    }
}
