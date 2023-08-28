package com.lasindu.clothfy_store.repository;

import com.lasindu.clothfy_store.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Lasindu Anjana
 * @email lasindua@gmail.com
 * @createdDate 8/28/23
 **/

public interface ProductRepository extends JpaRepository<Product, Integer> {
}
