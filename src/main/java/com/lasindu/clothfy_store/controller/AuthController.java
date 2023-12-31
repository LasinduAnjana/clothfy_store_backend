package com.lasindu.clothfy_store.controller;

import com.lasindu.clothfy_store.dto.request.AuthReqDTO;
import com.lasindu.clothfy_store.dto.request.RegisterReqDTO;
import com.lasindu.clothfy_store.dto.response.AuthResDTO;
import com.lasindu.clothfy_store.service.AuthenticationService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

/**
 * @author Lasindu Anjana
 * @email lasindua@gmail.com
 * @createdDate 8/28/23
 **/

@RestController
@RequestMapping("api/v1/auth")
@RequiredArgsConstructor
//@CrossOrigin(origins = "http://localhost:3000")
public class AuthController {
    private final AuthenticationService service;

    @PostMapping("/register")
    public ResponseEntity<?> register(
            @RequestBody RegisterReqDTO request
    ) {
        return service.register(request);
    }

    @PostMapping("/admin/register")
    public ResponseEntity<?> adminRegister(
            @RequestBody RegisterReqDTO request
    ) {
        return service.adminRegister(request);
    }

    @PostMapping("/authenticate")
    public ResponseEntity<AuthResDTO> authenticate(
            @RequestBody AuthReqDTO request
    ) {
        return ResponseEntity.ok(service.authenticate(request));
    }

    @PostMapping("/refresh-token")
    public void refreshToken(
            HttpServletRequest request,
            HttpServletResponse response
    ) throws IOException {
        service.refreshToken(request, response);
    }
}
