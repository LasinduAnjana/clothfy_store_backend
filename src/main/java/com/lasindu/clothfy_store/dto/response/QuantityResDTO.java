package com.lasindu.clothfy_store.dto.response;

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
public class QuantityResDTO {
    private int extraSmall;
    private int small;
    private int medium;
    private int large;
    private int extraLarge;
    private int doubleExtraLarge;
}
