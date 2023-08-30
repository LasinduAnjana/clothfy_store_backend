package com.lasindu.clothfy_store.config.security.user;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * @author Lasindu Anjana
 * @email lasindua@gmail.com
 * @createdDate 8/28/23
 **/

@RequiredArgsConstructor
public enum Permission {
    USER_READ("user:read"),
    USER_UPDATE("user.update"),
    USER_DELETE("user:delete"),
    USER_CREATE("user.create"),
    ADMIN_READ("admin:read"),
    ADMIN_UPDATE("admin:update"),
    ADMIN_CREATE("admin:create"),
    ADMIN_DELETE("admin:delete");
//    ADMIN_DELETE("admin:delete"),
//    MANAGER_READ("management:read"),
//    MANAGER_UPDATE("management:update"),
//    MANAGER_CREATE("management:create"),
//    MANAGER_DELETE("management:delete");

    @Getter
    private final String permission;
}
