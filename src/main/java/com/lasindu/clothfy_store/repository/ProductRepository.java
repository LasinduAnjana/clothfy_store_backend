package com.lasindu.clothfy_store.repository;

import com.lasindu.clothfy_store.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

/**
 * @author Lasindu Anjana
 * @email lasindua@gmail.com
 * @createdDate 8/28/23
 **/

public interface ProductRepository extends JpaRepository<Product, Integer> {
    @Modifying
    @Query("""
            UPDATE Product p SET p.quantity = :quantity WHERE p.id = :id
            """)
   void updateQuantityById(int quantity, int id);
}
