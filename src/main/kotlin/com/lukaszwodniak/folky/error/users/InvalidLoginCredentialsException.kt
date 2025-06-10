package com.lukaszwodniak.folky.error.users

/**
 * InvalidLoginCredentialsException
 *
 * Created on: 2024-10-28
 * @author Łukasz Wodniak
 */

class InvalidLoginCredentialsException(errorMessage: String) : RuntimeException(errorMessage)