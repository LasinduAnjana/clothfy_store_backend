package com.lasindu.clothfy_store.service;

import com.lasindu.clothfy_store.dto.response.MessageResDTO;
import com.lasindu.clothfy_store.entity.Cart;
import com.lasindu.clothfy_store.entity.CartItem;
import com.lasindu.clothfy_store.entity.User;
import com.lasindu.clothfy_store.repository.CartItemRepository;
import com.lasindu.clothfy_store.repository.CartRepository;
import com.lasindu.clothfy_store.repository.ProductRepository;
import com.lasindu.clothfy_store.util.UserUtil;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

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
    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final ProductRepository productRepository;

    private final UserUtil userUtil;

    public Optional<List<CartItem>> getAllCartItemsByUser(UUID cartId){
        Optional<Cart> cart = cartRepository.findById(cartId);
        return cart.map(cartItemRepository::findAllByCart).orElse(null);
    }

    @Transactional
    public MessageResDTO clearCartByUser() {
        if (userUtil.getUserDetails().isPresent()) {
            Optional<Cart> cart = cartRepository.findById(userUtil.getUserDetails().get().getCart().getId());
            if (cart.isPresent()) {
                cartItemRepository.deleteAllByCart(cart.get());
                return new MessageResDTO("cart cleared");
            }
        }
        return new MessageResDTO("cart clear failed");
    }

    @Transactional
    public ResponseEntity<MessageResDTO> removeCartItem(UUID itemId) {
        if (userUtil.getUserDetails().isPresent()) {
            Optional<Cart> cart = cartRepository.findById(userUtil.getUserDetails().get().getCart().getId());
            if (cart.isPresent()) {
                cartItemRepository.deleteById(itemId);
                return new ResponseEntity<MessageResDTO>(new MessageResDTO("item removed from cart"), HttpStatus.OK);
            }
        }
        return new ResponseEntity<MessageResDTO>(new MessageResDTO("item not found in the cart"), HttpStatus.NOT_FOUND);
    }

    public boolean confirmSellByCart(UUID cartId) {
        Optional<Cart> cart = cartRepository.findById(cartId);
        if (cart.isPresent()) {
            Optional<List<CartItem>> itemList = cartItemRepository.findAllByCart(cart.get());
            itemList.ifPresent(cartItems -> cartItems.forEach(cartItem -> {
                productRepository.updateQuantityById(cartItem.getQuantity(), cartItem.getProduct().getId());
            }));
            return true;
        }
        return false;
    }
}
