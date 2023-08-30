package com.lasindu.clothfy_store.repository;

import com.lasindu.clothfy_store.entity.Image;
import com.lasindu.clothfy_store.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Lasindu Anjana
 * @email lasindua@gmail.com
 * @createdDate 8/29/23
 **/

@Repository
public interface ImageRepository extends JpaRepository<Image, Long> {
    boolean existsByFilename(String filename);
    List<Image> findAllByProductOrderByPlacement(Product product);
}
