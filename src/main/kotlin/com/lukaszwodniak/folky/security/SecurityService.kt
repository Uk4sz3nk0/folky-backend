package com.lukaszwodniak.folky.security

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserRecord
import com.lukaszwodniak.folky.error.users.InvalidLoginCredentialsException
import com.lukaszwodniak.folky.error.users.InvalidOldPasswordException
import com.lukaszwodniak.folky.error.users.InvalidRefreshTokenException
import com.lukaszwodniak.folky.records.*
import com.lukaszwodniak.folky.service.users.UserService
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
class SecurityService(
    private val userService: UserService,
    private val firebaseAuth: FirebaseAuth,
) {

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

    fun changePassword(passwordChangeRequest: PasswordChangeRequest) {
        val contextUser = userService.getUserFromContext()

        val userRecord = firebaseAuth.getUser(contextUser?.uid)
        val userEmail = userRecord?.email

        validateUserOldPassword(userEmail!!, passwordChangeRequest.oldPassword)

        firebaseAuth.getUserByEmail(userEmail)

        val updateUserRequest: UserRecord.UpdateRequest = UserRecord.UpdateRequest(contextUser?.uid)
            .setPassword(passwordChangeRequest.newPassword)

        firebaseAuth.updateUser(updateUserRequest)
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

    private fun validateUserOldPassword(email: String, oldPassword: String) {
       try {
            val loginRequest = LoginRequest(email, oldPassword)
           sendSignInRequest(loginRequest)
       } catch (exception: Exception) {
           throw InvalidOldPasswordException("Given old password is invalid")
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