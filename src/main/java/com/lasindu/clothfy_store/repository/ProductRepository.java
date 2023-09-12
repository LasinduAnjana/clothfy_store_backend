package com.lasindu.clothfy_store.repository;

import com.lasindu.clothfy_store.entity.Product;
import com.lasindu.clothfy_store.entity.ProductCategory;
import com.lasindu.clothfy_store.entity.ProductType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * @author Lasindu Anjana
 * @email lasindua@gmail.com
 * @createdDate 8/28/23
 **/

@Repository
public interface ProductRepository extends JpaRepository<Product, UUID> {
    @Modifying
    @Query("""
            UPDATE Product p SET p.quantity = :quantity WHERE p.id = :id
            """)
   void updateQuantityById(int quantity, UUID id);

    @Query("""
    SELECT p from Product p ORDER BY p.id DESC LIMIT 10
""")
    Optional<List<Product>> findTop10();

    Optional<List<Product>> findAllByTypeOrderById(ProductType type);

    Optional<List<Product>> findAllByCategoryOrderById(ProductCategory category);
}
