package com.lasindu.clothfy_store.service;

import com.lasindu.clothfy_store.dto.response.UserResDTO;
import com.lasindu.clothfy_store.entity.User;
import com.lasindu.clothfy_store.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * @author Lasindu Anjana
 * @email lasindua@gmail.com
 * @createdDate 8/28/23
 **/

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final AuthenticationService authenticationService;

    public Optional<User> getUser(Long id) {
        return userRepository.findById(id);
    }

    public boolean removeUser(Long id) {
        Optional<User> user = userRepository.findById(id);
        if (user.isPresent()) {
            authenticationService.revokeAllUserTokens(user.get());
            userRepository.deleteById(user.get().getId());
            return true;
        }
        return false;
    }

}
