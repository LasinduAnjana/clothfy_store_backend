package com.lasindu.clothfy_store.dto.request;

import com.lasindu.clothfy_store.entity.ProductCategory;
import com.lasindu.clothfy_store.entity.ProductSize;
import com.lasindu.clothfy_store.entity.ProductType;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Set;

/**
 * @author Lasindu Anjana
 * @email lasindua@gmail.com
 * @createdDate 8/28/23
 **/

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AddProductReqDTO {
    private String title;
    private String description;
    private String material;
    private int weight;
    private QuantityReqDTO quantity;
    private Double price;
    @Enumerated(EnumType.STRING)
    private Set<ProductSize> size;
    private ProductCategory category;
    private ProductType type;
    // REFERENCE: imageDTO
    private List<String> imageLinks;
}
