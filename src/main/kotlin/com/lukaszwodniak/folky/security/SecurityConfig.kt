package com.lukaszwodniak.folky.security

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter


@Configuration
class SecurityConfiguration(private val tokenAuthenticationFilter: TokenAuthenticationFilter) {

    @Bean
    @Throws(Exception::class)
    fun securityFilterChain(http: HttpSecurity): SecurityFilterChain {
        http
            .authorizeHttpRequests { authManager ->
                authManager
                    .requestMatchers(HttpMethod.POST, *WHITELISTED_API_ENDPOINTS)
                    .permitAll()
                    .requestMatchers(HttpMethod.GET, *WHITELISTED_API_ENDPOINTS)
                    .permitAll()
                    .anyRequest()
                    .authenticated()
            }
            .addFilterBefore(tokenAuthenticationFilter, UsernamePasswordAuthenticationFilter::class.java)
            .csrf { csrf -> csrf.disable() }

        return http.build()
    }

    companion object {
        private val WHITELISTED_API_ENDPOINTS =
            arrayOf(
                "/api/users",
                "/api/users/login",
                "/api/users/refresh-token",
                "/api/users/register-user",
                "/hello/not-auth/",
                "/api/users/register-as-dancing-team",
                "/api/teams/get-teams",
                "/api/teams/get-by-id",
                "/api/files/get-logo",
                "/api/files/get-banner",
                "/api/files/get-gallery-urls",
                "/api/files/get-image",
                "/api/region/get-regions",
                "/api/region/get-by-id"
            )
    }
}