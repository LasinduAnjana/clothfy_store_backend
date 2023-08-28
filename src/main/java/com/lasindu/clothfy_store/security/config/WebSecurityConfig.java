package com.lasindu.clothfy_store.security.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutHandler;

import static com.lasindu.clothfy_store.security.user.Role.ADMIN;
import static com.lasindu.clothfy_store.security.user.Role.MANAGER;
import static com.lasindu.clothfy_store.security.user.Permission.ADMIN_CREATE;
import static org.springframework.http.HttpMethod.GET;
import static org.springframework.http.HttpMethod.POST;

/**
 * @author Lasindu Anjana
 * @email lasindua@gmail.com
 * @createdDate 8/28/23
 **/

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@EnableMethodSecurity
public class WebSecurityConfig {
    private final JwtAuthenticationFilter jwtAuthFilter;
    private final AuthenticationProvider authenticationProvider;
    private final LogoutHandler logoutHandler;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> {
                    auth
                        .requestMatchers("api/v1/auth/**").permitAll()
                        .requestMatchers("api/v1/product/**").hasAnyRole(ADMIN.name(), MANAGER.name())
                        .requestMatchers(GET, "api/v1/product/**").permitAll()
                        .requestMatchers(POST, "api/v1/product").hasAnyAuthority(ADMIN_CREATE.name())
                        .anyRequest().authenticated();
                })
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
                .logout(logout -> logout
                    .logoutUrl("/api/v1/auth/logout")
                    .addLogoutHandler(logoutHandler)
                    .logoutSuccessHandler(((request, response, authentication) -> SecurityContextHolder.clearContext()))
                );

        return httpSecurity.build();

    }
}
