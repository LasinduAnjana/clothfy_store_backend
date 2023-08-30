package com.lasindu.clothfy_store.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Lasindu Anjana
 * @email lasindua@gmail.com
 * @createdDate 8/29/23
 **/

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ImageDTO {
    private long productId;
    private int placement;

    // use exact same name (same file) that send to upload image request
    private String fileName;
}
