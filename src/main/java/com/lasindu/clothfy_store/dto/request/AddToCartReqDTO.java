package com.lasindu.clothfy_store.dto.request;

import com.lasindu.clothfy_store.entity.ProductSize;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Lasindu Anjana
 * @email lasindua@gmail.com
 * @createdDate 9/12/23
 **/

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AddToCartReqDTO {
    private ProductSize size;
    private int quantity;
}
