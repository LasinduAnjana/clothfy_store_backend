package com.lasindu.clothfy_store.repository;

import com.lasindu.clothfy_store.entity.Image;
import com.lasindu.clothfy_store.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Lasindu Anjana
 * @email lasindua@gmail.com
 * @createdDate 8/28/23
 **/

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    @Modifying
    @Query("""
            UPDATE Product p SET p.quantity = :quantity WHERE p.id = :id
            """)
   void updateQuantityById(int quantity, Long id);

//    @Modifying
//    @Query("""
//            UPDATE Product p SET p.images = :images WHERE p.id = :id
//            """)
//    void updateImagesByProductId(List<Image> images, Long id);
}
