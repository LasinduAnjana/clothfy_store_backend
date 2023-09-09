package com.lasindu.clothfy_store.dto.response;

import com.lasindu.clothfy_store.entity.ProductSize;
import com.lasindu.clothfy_store.entity.ProductType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Lasindu Anjana
 * @email lasindua@gmail.com
 * @createdDate 9/5/23
 **/

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductDTO {
    private long id;
    private String title;
    private String description;
    private String material;
    private int weight;
    private int quantity;
    private Double price;
    private ProductSize size;
    private ProductType type;
    private String[] imageLinks;
}
