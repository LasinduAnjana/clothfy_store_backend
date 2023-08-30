package com.lasindu.clothfy_store.controller;

import com.lasindu.clothfy_store.dto.response.MessageResDTO;
import com.lasindu.clothfy_store.dto.response.UserResDTO;
import com.lasindu.clothfy_store.entity.User;
import com.lasindu.clothfy_store.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

/**
 * @author Lasindu Anjana
 * @email lasindua@gmail.com
 * @createdDate 8/28/23
 **/

@RestController
@RequestMapping("api/v1/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @DeleteMapping("/{id}")
    public ResponseEntity<MessageResDTO> deleteUser(@PathVariable Long id) {
        boolean result = userService.removeUser(id);
        if (result) {
            return new ResponseEntity<MessageResDTO>(new MessageResDTO("User removed successfull"), HttpStatus.OK);
        } else {
            return new ResponseEntity<MessageResDTO>(new MessageResDTO("Operation failed. User id invalid."), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getUser(@PathVariable Long id) {
        Optional<User> result = userService.getUser(id);
        return result.map(user -> new ResponseEntity<Object>(new UserResDTO(
                user.getFirstName(),
                user.getLastName(),
                user.getEmail(),
                user.getRole()), HttpStatus.OK))
        .orElseGet(() -> new ResponseEntity<>(new MessageResDTO("User for given id not found"), HttpStatus.NOT_FOUND));
    }
}
