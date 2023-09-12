package com.lasindu.clothfy_store.dto.response;

import com.lasindu.clothfy_store.dto.request.QuantityReqDTO;
import com.lasindu.clothfy_store.entity.ProductCategory;
import com.lasindu.clothfy_store.entity.ProductSize;
import com.lasindu.clothfy_store.entity.ProductType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Set;
import java.util.UUID;

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
    private UUID id;
    private String title;
    private String description;
    private String material;
    private int weight;
    private QuantityReqDTO quantity;
    private Double price;
    private Set<ProductSize> size;
    private ProductType type;
    private ProductCategory category;
    private List<String> imageLinks;
}
