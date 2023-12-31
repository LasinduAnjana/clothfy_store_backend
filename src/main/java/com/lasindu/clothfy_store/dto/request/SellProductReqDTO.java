package com.lasindu.clothfy_store.dto.request;

import com.lasindu.clothfy_store.entity.ProductSize;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Lasindu Anjana
 * @email lasindua@gmail.com
 * @createdDate 8/28/23
 **/


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SellProductReqDTO {
    private int quantity;

    @Enumerated(EnumType.STRING)
    private ProductSize size;
}
