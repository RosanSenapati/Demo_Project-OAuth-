package com.Rosan.FullstackBackend.Config;

import com.Rosan.FullstackBackend.model.User;
import com.Rosan.FullstackBackend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.security.SecureRandom;
import java.util.Base64;
import java.util.List;
import java.util.Optional;

@Configuration
public class SecurityConfig {
    @Autowired
    private UserRepository userRepository;


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .cors(Customizer.withDefaults())
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(registry -> {
                    registry.requestMatchers("/user", "/login", "/oauth2/**,").permitAll(); //Public Route
                    registry.requestMatchers("/users").authenticated(); //Private Authenticated Routes
                    registry.anyRequest().authenticated(); // Protect other routes
                })
                .oauth2Login(oauth2 -> oauth2
                        .successHandler(customSuccessHandler()) );

        return http.build();
    }

    @Bean
    public AuthenticationSuccessHandler customSuccessHandler() {
        return (request, response, authentication) -> {
            OAuth2User oauthUser = (OAuth2User) authentication.getPrincipal();
          System.out.println(oauthUser);
            String username = oauthUser.getAttribute("name");
            String email = oauthUser.getAttribute("email");

            // Generate a random password
            String randomPassword = generateRandomPassword();

            // Check if user already exists
            Optional<User> existingUser = userRepository.findByEmail(email);
            if (existingUser.isEmpty()) {
                // Create new user and save
                User user = new User();
                user.setName(username);
                user.setEmail(email);
                user.setPassword(randomPassword);
                userRepository.save(user);
            }// Get user attribute (Change as needed)

            // Redirect Home Url
            String redirectUrl = "http://localhost:5173/home/" + username;

            response.sendRedirect(redirectUrl); // Redirect to Home Page
        };
    }

    private String generateRandomPassword() {
        SecureRandom random = new SecureRandom();
        byte[] bytes = new byte[16]; // 16-byte random password
        random.nextBytes(bytes);
        return Base64.getEncoder().encodeToString(bytes);
    }
    //For Cors Error
    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowedOrigins(List.of("http://localhost:5173"));
        config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        config.setAllowedHeaders(List.of("Authorization", "Content-Type"));
        config.setAllowCredentials(true);
        source.registerCorsConfiguration("/**", config);
        return new CorsFilter(source);
    }

}
