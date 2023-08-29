package com.lasindu.clothfy_store.dto.response;

import com.lasindu.clothfy_store.config.security.user.Role;
import com.lasindu.clothfy_store.entity.Token;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author Lasindu Anjana
 * @email lasindua@gmail.com
 * @createdDate 8/28/23
 **/

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserResDTO {
    private String firstName;
    private String lastName;
    private String email;
    private Role role;
}
