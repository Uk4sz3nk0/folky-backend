package com.lukaszwodniak.folky.security

import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component
import java.util.*


@Component
class AuthenticatedUserIdProvider {
    val userId: String
        get() = Optional.ofNullable(SecurityContextHolder.getContext().authentication)
            .map(Authentication::getPrincipal)
            .filter { obj: Any? -> String::class.java.isInstance(obj) }
            .map { obj: Any? -> String::class.java.cast(obj) }
            .orElseThrow { IllegalStateException() }
}