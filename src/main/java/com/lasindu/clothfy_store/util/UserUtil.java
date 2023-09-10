package com.lasindu.clothfy_store.util;

import com.lasindu.clothfy_store.entity.User;
import com.lasindu.clothfy_store.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * @author Lasindu Anjana
 * @email lasindua@gmail.com
 * @createdDate 9/9/23
 **/

@RequiredArgsConstructor
@Component
public class UserUtil {
    private final UserRepository userRepository;

    public Optional<User> getUserDetails() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (principal instanceof UserDetails) {
            String username = ((UserDetails)principal).getUsername();
            return userRepository.findByEmail(username);
        } else {
            String username = principal.toString();
            return Optional.empty();
        }
    }
}
