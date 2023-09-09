package com.lasindu.clothfy_store.service;

import com.lasindu.clothfy_store.entity.Cart;
import com.lasindu.clothfy_store.entity.CartItem;
import com.lasindu.clothfy_store.entity.User;
import com.lasindu.clothfy_store.repository.CartItemRepository;
import com.lasindu.clothfy_store.repository.CartRepository;
import com.lasindu.clothfy_store.repository.ProductRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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

    public Optional<List<CartItem>> getAllCartItemsByUser(long cartId){
        Optional<Cart> cart = cartRepository.findById(cartId);
        return cart.map(cartItemRepository::findAllByCart).orElse(null);
    }

    @Transactional
    public boolean clearCartByUser(long cartId) {
        Optional<Cart> cart = cartRepository.findById(cartId);
        if (cart.isPresent()) {
            cartItemRepository.deleteAllByCart(cart.get());
            return true;
        }
        return false;
    }

    public boolean confirmSellByCart(long cartId) {
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
