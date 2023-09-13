package com.lasindu.clothfy_store.repository;

import com.lasindu.clothfy_store.entity.CartItem;
import com.lasindu.clothfy_store.entity.User;
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
      Optional<List<CartItem>> findAllByUser(User user);

      void deleteAllByUser(User user);
}
