package com.lasindu.clothfy_store.dto.request;

import com.lasindu.clothfy_store.config.security.user.Role;
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
public class RegisterReqDTO {
    private String firstname;
    private String lastname;
    private String email;
    private String password;
}
