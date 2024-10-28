package com.lukaszwodniak.folky.security

import com.lukaszwodniak.folky.error.users.InvalidLoginCredentialsException
import com.lukaszwodniak.folky.error.users.InvalidRefreshTokenException
import com.lukaszwodniak.folky.records.LoginRequest
import com.lukaszwodniak.folky.records.LoginResponse
import com.lukaszwodniak.folky.records.RefreshTokenRequest
import com.lukaszwodniak.folky.records.RefreshTokenResponse
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.MediaType
import org.springframework.stereotype.Service
import org.springframework.web.client.HttpClientErrorException
import org.springframework.web.client.RestClient
import org.springframework.web.util.UriBuilder


/**
 * SecurityService
 *
 * Created on: 2024-10-28
 * @author Łukasz Wodniak
 */

@Service
class SecurityService {

    @Value("\${com.lukaszwodniak.folky.firebase.web-api-key}")
    private lateinit var webApiKey: String

    fun login(emailId: String, password: String): LoginResponse? {
        val requestBody = LoginRequest(emailId, password)
        return sendSignInRequest(requestBody)
    }

    fun exchangeRefreshToken(refreshToken: String?): RefreshTokenResponse? {
        val requestBody: RefreshTokenRequest? = refreshToken?.let { RefreshTokenRequest(it, REFRESH_TOKEN_GRANT_TYPE) }
        return requestBody?.let { sendRefreshTokenRequest(it) }
    }

    private fun sendSignInRequest(firebaseSignInRequest: LoginRequest): LoginResponse? {
        try {
            return RestClient.create(SIGN_IN_BASE_URL)
                .post()
                .uri { uriBuilder: UriBuilder ->
                    uriBuilder
                        .queryParam(API_KEY_PARAM, webApiKey)
                        .build()
                }
                .body(firebaseSignInRequest)
                .contentType(MediaType.APPLICATION_JSON)
                .retrieve()
                .body(LoginResponse::class.java)
        } catch (exception: HttpClientErrorException) {
            if (exception.responseBodyAsString.contains(INVALID_CREDENTIALS_ERROR)) {
                throw InvalidLoginCredentialsException("Invalid login credentials provided")
            }
            throw exception
        }
    }

    private fun sendRefreshTokenRequest(refreshTokenRequest: RefreshTokenRequest): RefreshTokenResponse? {
        try {
            return RestClient.create(REFRESH_TOKEN_BASE_URL)
                .post()
                .uri { uriBuilder: UriBuilder ->
                    uriBuilder
                        .queryParam(API_KEY_PARAM, webApiKey)
                        .build()
                }
                .body(refreshTokenRequest)
                .contentType(MediaType.APPLICATION_JSON)
                .retrieve()
                .body(RefreshTokenResponse::class.java)
        } catch (exception: HttpClientErrorException) {
            if (exception.responseBodyAsString.contains(INVALID_REFRESH_TOKEN_ERROR)) {
                throw InvalidRefreshTokenException("Invalid refresh token provided")
            }
            throw exception
        }
    }

    companion object {
        const val API_KEY_PARAM: String = "key"
        const val INVALID_CREDENTIALS_ERROR: String = "INVALID_LOGIN_CREDENTIALS"
        const val SIGN_IN_BASE_URL: String = "https://identitytoolkit.googleapis.com/v1/accounts:signInWithPassword"
        const val REFRESH_TOKEN_GRANT_TYPE: String = "refresh_token"
        const val INVALID_REFRESH_TOKEN_ERROR: String = "INVALID_REFRESH_TOKEN"
        const val REFRESH_TOKEN_BASE_URL: String = "https://securetoken.googleapis.com/v1/token"
    }
}