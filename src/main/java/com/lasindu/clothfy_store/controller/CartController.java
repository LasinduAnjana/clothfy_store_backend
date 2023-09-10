package com.lasindu.clothfy_store.controller;

import com.lasindu.clothfy_store.dto.response.MessageResDTO;
import com.lasindu.clothfy_store.entity.CartItem;
import com.lasindu.clothfy_store.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * @author Lasindu Anjana
 * @email lasindua@gmail.com
 * @createdDate 9/4/23
 **/

@RestController
@RequestMapping("api/v1/user")
@RequiredArgsConstructor
public class CartController {
    private final CartService cartService;

    @GetMapping("/{cartId}")
    public ResponseEntity<Optional<List<CartItem>>> getAllCartItems(@PathVariable long cartId) {
        Optional<List<CartItem>> items = cartService.getAllCartItemsByUser(cartId);
        return new ResponseEntity<>(items, HttpStatus.OK);
    }

    @DeleteMapping("/user/cart/")
    public ResponseEntity<MessageResDTO> clearCart() {
        return new ResponseEntity<>(cartService.clearCartByUser(), HttpStatus.OK);
    }

    @DeleteMapping("/user/cart/{itemId}")
    public ResponseEntity<MessageResDTO> clearCart(@PathVariable long itemId) {
        return cartService.removeCartItem(itemId);
    }
}
