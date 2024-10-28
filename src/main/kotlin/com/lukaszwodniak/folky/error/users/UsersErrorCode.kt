package com.lukaszwodniak.folky.error.users

/**
 * UsersErrorCode
 *
 * Created on: 2024-10-28
 * @author Łukasz Wodniak
 */

enum class UsersErrorCode {

    EMAIL_IS_ALREADY_IN_USE,
    INVALID_LOGIN_CREDENTIALS,
    INVALID_REFRESH_TOKEN;

    fun getCode(): Int {
        return 700 + ordinal
    }

}