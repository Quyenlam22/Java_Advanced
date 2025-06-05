package com.group_6.book_store.config;

import com.group_6.book_store.util.JwtAuthenticationFilter;
import com.group_6.book_store.util.JwtUtil;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final JwtUtil jwtUtil;
    private final UserDetailsService userDetailsService;

    public SecurityConfig(JwtUtil jwtUtil, UserDetailsService userDetailsService) {
        this.jwtUtil = jwtUtil;
        this.userDetailsService = userDetailsService;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, AuthenticationManager authenticationManager) throws Exception {
        JwtAuthenticationFilter jwtAuthenticationFilter = new JwtAuthenticationFilter(jwtUtil, userDetailsService);

        http
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/v1/auth/**").permitAll()
                        .requestMatchers("/api/v1/users").hasRole("ADMIN")
                        .requestMatchers("/api/v1/users/search").hasRole("ADMIN")
                        .requestMatchers("/api/v1/users/{id}").hasAnyRole("ADMIN", "CUSTOMER", "AUTHOR")
                        .requestMatchers("/api/v1/users/me").authenticated()

                        .requestMatchers("/api/v1/authors").hasRole("ADMIN")
                        .requestMatchers("/api/v1/authors/search").hasRole("ADMIN")
                        .requestMatchers("/api/v1/authors/{id}").hasAnyRole("ADMIN", "CUSTOMER", "AUTHOR")
                        .requestMatchers("/api/v1/authors/**").hasRole("ADMIN")

                        .requestMatchers("/api/v1/author-posts").hasAnyRole("ADMIN", "CUSTOMER", "AUTHOR")
                        .requestMatchers("/api/v1/author-posts/search").hasAnyRole("ADMIN", "CUSTOMER", "AUTHOR")
                        .requestMatchers("/api/v1/author-posts/{id}").hasAnyRole("ADMIN", "CUSTOMER", "AUTHOR")
                        .requestMatchers("/api/v1/author-posts/**").hasAnyRole("ADMIN", "AUTHOR") // POST, PUT, DELETE

                        .requestMatchers("/api/v1/categories").hasAnyRole("ADMIN", "CUSTOMER", "AUTHOR")
                        .requestMatchers("/api/v1/categories/search").hasAnyRole("ADMIN", "CUSTOMER", "AUTHOR")
                        .requestMatchers("/api/v1/categories/{id}").hasAnyRole("ADMIN", "CUSTOMER", "AUTHOR")
                        .requestMatchers("/api/v1/categories/**").hasRole("ADMIN") // POST, PUT, DELETE

                        .requestMatchers("/api/v1/books").hasAnyRole("ADMIN", "CUSTOMER", "AUTHOR")
                        .requestMatchers("/api/v1/books/search").hasAnyRole("ADMIN", "CUSTOMER", "AUTHOR")
                        .requestMatchers("/api/v1/books/category/{categoryId}").hasAnyRole("ADMIN", "CUSTOMER", "AUTHOR")
                        .requestMatchers("/api/v1/books/{id}").hasAnyRole("ADMIN", "CUSTOMER", "AUTHOR")
                        .requestMatchers("/api/v1/books/**").hasRole("ADMIN") // POST, PUT, DELETE

                        .requestMatchers("/api/v1/orders").hasRole("ADMIN")
                        .requestMatchers("/api/v1/orders/me").hasRole("CUSTOMER")
                        .requestMatchers("/api/v1/orders/{id}").hasAnyRole("ADMIN", "CUSTOMER")
                        .requestMatchers("/api/v1/orders/**").hasAnyRole("ADMIN", "CUSTOMER")
                        .requestMatchers("/api/v1/cart/**").hasRole("CUSTOMER")
                        .anyRequest().authenticated()
                )
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authenticationManager(authenticationManager)
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.addAllowedOrigin("*");
        configuration.addAllowedMethod("*");
        configuration.addAllowedHeader("*");
        configuration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}