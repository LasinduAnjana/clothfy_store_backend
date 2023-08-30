package com.lasindu.clothfy_store.config.security.user;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static com.lasindu.clothfy_store.config.security.user.Permission.*;

/**
 * @author Lasindu Anjana
 * @email lasindua@gmail.com
 * @createdDate 8/28/23
 **/

@Getter
@RequiredArgsConstructor
public enum Role {
//    USER(Collections.emptySet()),
    USER(
            Set.of(
            USER_READ,
            USER_UPDATE,
            USER_DELETE,
            USER_CREATE
            )),
    ADMIN(
            Set.of(
            ADMIN_READ,
            ADMIN_UPDATE,
            ADMIN_DELETE,
            ADMIN_CREATE,
            USER_READ,
            USER_UPDATE,
            USER_DELETE,
            USER_CREATE
//            ADMIN_CREATE,
//            MANAGER_READ,
//            MANAGER_UPDATE,
//            MANAGER_DELETE,
//            MANAGER_CREATE
            )
//  ),
//    MANAGER(
//            Set.of(
//            MANAGER_READ,
//            MANAGER_UPDATE,
//            MANAGER_DELETE,
//            MANAGER_CREATE
//            )
  );

    @Getter
    private final Set<Permission> permissions;

    public List<SimpleGrantedAuthority> getAuthorities() {
        var authorities = getPermissions()
                .stream()
                .map(permission -> new SimpleGrantedAuthority(permission.getPermission()))
                .collect(Collectors.toList());
        authorities.add(new SimpleGrantedAuthority("ROLE_" + this.name()));
        return authorities;
    }
}
