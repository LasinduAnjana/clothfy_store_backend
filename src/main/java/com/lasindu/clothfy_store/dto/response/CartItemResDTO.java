package com.lasindu.clothfy_store.dto.response;

import com.lasindu.clothfy_store.entity.ProductSize;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

/**
 * @author Lasindu Anjana
 * @email lasindua@gmail.com
 * @createdDate 9/4/23
 **/

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CartItemResDTO {
    private UUID id;
    private String title;
    private ProductSize size;
    private int quantity;
    private Double price;
    private String imageSrc;
}
