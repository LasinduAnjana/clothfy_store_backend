package com.lasindu.clothfy_store.config.security;

import com.lasindu.clothfy_store.config.security.user.Permission;
import com.lasindu.clothfy_store.config.security.user.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

import static com.lasindu.clothfy_store.config.security.user.Permission.*;
import static com.lasindu.clothfy_store.config.security.user.Role.ADMIN;
import static com.lasindu.clothfy_store.config.security.user.Role.USER;
import static org.springframework.http.HttpMethod.*;

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
                .csrf(AbstractHttpConfigurer::disable)
                .cors(httpSecurityCorsConfigurer -> {
                    CorsConfiguration configuration = new CorsConfiguration();
                    configuration.setAllowedOrigins(Arrays.asList("*"));
                    configuration.setAllowedMethods(Arrays.asList("*"));
                    configuration.setAllowedHeaders(Arrays.asList("*"));
                    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
                    source.registerCorsConfiguration("/**", configuration);
                    httpSecurityCorsConfigurer.configurationSource(source);
                })
                .authorizeHttpRequests(auth -> {
                    auth
                            .requestMatchers("api/v1/auth/**").permitAll()
                            .requestMatchers("api/v1/public/**").permitAll()
                            .requestMatchers("api/v1/user/**").hasAnyRole(USER.name(), ADMIN.name())
                            .requestMatchers("api/v1/admin/**").hasAnyRole(ADMIN.name())
                            .anyRequest().authenticated();





//                        .requestMatchers("api/v1/auth/**").permitAll()
//                        .requestMatchers("api/v1/product/**").permitAll()
////                        .requestMatchers(GET, "api/v1/product/**").permitAll()
////                        .requestMatchers(POST, "api/v1/product").hasAnyAuthority(Permission.ADMIN_CREATE.name())
////                        .requestMatchers(PUT, "api/v1/product/buy/**").hasAnyRole(USER.name(), ADMIN.name())
////                        .requestMatchers(DELETE, "api/v1/product/**").hasAnyAuthority(Permission.ADMIN_DELETE.name())
////                        .requestMatchers(GET, "api/v1/image/**").permitAll()
////                        .requestMatchers(POST, "api/v1/product/**").hasAnyAuthority(Permission.ADMIN_CREATE.name())
//                        .requestMatchers(GET, "api/v1/product/**").permitAll()
//                        .requestMatchers(POST, "api/v1/product").hasAnyAuthority(ADMIN_CREATE.name(), USER_CREATE.name())
//                        .requestMatchers(PUT, "api/v1/product/**").hasAnyAuthority(USER_UPDATE.name(), ADMIN_UPDATE.name())
//                        .anyRequest().authenticated();
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
