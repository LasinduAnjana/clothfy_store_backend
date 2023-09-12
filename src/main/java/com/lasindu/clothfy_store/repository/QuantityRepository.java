package com.lasindu.clothfy_store.repository;

import com.lasindu.clothfy_store.entity.Product;
import com.lasindu.clothfy_store.entity.Quantity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

/**
 * @author Lasindu Anjana
 * @email lasindua@gmail.com
 * @createdDate 9/12/23
 **/

public interface QuantityRepository extends JpaRepository<Quantity, UUID> {
}
