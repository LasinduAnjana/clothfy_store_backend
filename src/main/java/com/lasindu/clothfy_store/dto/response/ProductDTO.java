package com.lasindu.clothfy_store.dto.response;

import com.lasindu.clothfy_store.dto.request.QuantityDTO;
import com.lasindu.clothfy_store.entity.ProductCategory;
import com.lasindu.clothfy_store.entity.ProductSize;
import com.lasindu.clothfy_store.entity.ProductType;
import com.lasindu.clothfy_store.entity.Quantity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Set;

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
    private QuantityDTO quantity;
    private Double price;
    private Set<ProductSize> size;
    private ProductType type;
    private ProductCategory category;
    private List<String> imageLinks;
}
