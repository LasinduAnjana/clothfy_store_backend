package com.lasindu.clothfy_store.repository;

import com.lasindu.clothfy_store.entity.Cart;
import com.lasindu.clothfy_store.entity.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

/**
 * @author Lasindu Anjana
 * @email lasindua@gmail.com
 * @createdDate 9/3/23
 **/

public interface CartRepository extends JpaRepository<Cart, Long> {
    Optional<Cart> findCartByUserId(Long id);
}
