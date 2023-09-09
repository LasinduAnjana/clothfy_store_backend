package com.lasindu.clothfy_store.dto.response;

import com.lasindu.clothfy_store.entity.Cart;
import com.lasindu.clothfy_store.entity.Product;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

/**
 * @author Lasindu Anjana
 * @email lasindua@gmail.com
 * @createdDate 9/4/23
 **/

public class CartItemDTO {
    private Long id;
    private Product product;
    private Cart cart;
    private int quantity;
}
