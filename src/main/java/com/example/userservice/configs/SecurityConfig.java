package com.example.userservice.configs;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;


// @Configuration annotation indicates that a class declares one or more @Bean methods and
// may be processed by the Spring container to generate bean definitions and service requests for those beans at runtime
@Configuration
public class SecurityConfig {

    // This method creates a SecurityFilterChain bean that configures the security of the application
    // All requests coming to the application will be passed through this SecurityFilterChain
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        // Configure the security of the application
        http.authorizeHttpRequests((requests) -> {
            try {
                // Permit all requests
                requests.anyRequest().permitAll()
                // Disable CSRF protection
                        .and().csrf().disable();
            }
            catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
        // Build the http security configuration
        return http.build();
    }

}
