package com.lukaszwodniak.folky.security

import com.fasterxml.jackson.core.JsonProcessingException
import com.fasterxml.jackson.databind.ObjectMapper
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthException
import jakarta.servlet.FilterChain
import jakarta.servlet.ServletException
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ProblemDetail
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter
import java.io.IOException
import java.util.*


/**
 * TokenAuthenticationFilter
 *
 * Created on: 2024-10-28
 * @author Łukasz Wodniak
 */

@Component
class TokenAuthenticationFilter(private val firebaseAuth: FirebaseAuth, private val objectMapper: ObjectMapper) :
    OncePerRequestFilter() {

    @Throws(IOException::class, ServletException::class)
    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        val authorizationHeader = request.getHeader(AUTHORIZATION_HEADER)

        if (authorizationHeader != null && authorizationHeader.startsWith(BEARER_PREFIX)) {
            val token = authorizationHeader.replace(BEARER_PREFIX, "").trim()
            val userId: Optional<String> = extractUserIdFromToken(token)

            if (userId.isPresent) {
                val authentication = UsernamePasswordAuthenticationToken(userId.get(), null, null)
                authentication.details = WebAuthenticationDetailsSource().buildDetails(request)
                SecurityContextHolder.getContext().authentication = authentication
            } else {
                setAuthErrorDetails(response)
                return
            }
        }
        filterChain.doFilter(request, response)
    }

    private fun extractUserIdFromToken(token: String): Optional<String> {
        try {
            val firebaseToken = firebaseAuth.verifyIdToken(token, true)
            val userId = firebaseToken.claims[USER_ID_CLAIM].toString()
            return Optional.of(userId)
        } catch (exception: FirebaseAuthException) {
            return Optional.empty()
        }
    }

    @Throws(JsonProcessingException::class, IOException::class)
    private fun setAuthErrorDetails(response: HttpServletResponse) {
        val unauthorized: HttpStatus = HttpStatus.UNAUTHORIZED
        response.status = unauthorized.value()
        response.contentType = MediaType.APPLICATION_JSON_VALUE
        val problemDetail =
            ProblemDetail.forStatusAndDetail(unauthorized, "Authentication failure: Token missing, invalid or expired")
        response.writer.write(objectMapper.writeValueAsString(problemDetail))
    }

    companion object {
        private const val BEARER_PREFIX = "Bearer "
        private const val USER_ID_CLAIM = "user_id"
        private const val AUTHORIZATION_HEADER = "Authorization"
    }
}