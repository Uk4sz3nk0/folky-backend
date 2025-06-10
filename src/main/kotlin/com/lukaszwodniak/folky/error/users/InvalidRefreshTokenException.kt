package com.lukaszwodniak.folky.error.users

/**
 * InvalidRefreshTokenException
 *
 * Created on: 2024-10-24
 * @author Łukasz Wodniak
 */

class InvalidRefreshTokenException(errorMessage: String) : RuntimeException(errorMessage)