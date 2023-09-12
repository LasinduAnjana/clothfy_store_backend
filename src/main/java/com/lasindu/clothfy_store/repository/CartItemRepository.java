package com.lasindu.clothfy_store.repository;

import com.lasindu.clothfy_store.entity.Cart;
import com.lasindu.clothfy_store.entity.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * @author Lasindu Anjana
 * @email lasindua@gmail.com
 * @createdDate 9/3/23
 **/

public interface CartItemRepository extends JpaRepository<CartItem, UUID> {
      Optional<List<CartItem>> findAllByCart(Cart cart);

      void deleteAllByCart(Cart cart);
}
