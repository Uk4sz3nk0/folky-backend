package com.lukaszwodniak.folky.security

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.web.SecurityFilterChain


@Configuration
class SecurityConfig {

    @Bean
    fun securityFilterChain(http: HttpSecurity): SecurityFilterChain {
        http
            .authorizeRequests { authorizeRequests ->
                authorizeRequests
                    .anyRequest().permitAll() // pozwala na dostęp do wszystkich endpointów bez autoryzacji
            }
            .csrf { csrf -> csrf.disable() } // wyłącza CSRF
        return http.build()
    }
}