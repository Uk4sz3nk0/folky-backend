package com.lukaszwodniak.folky.error.users

/**
 * EmailInUseException
 *
 * Created on: 2024-10-28
 * @author Łukasz Wodniak
 */

class EmailInUseException(errorMessage: String) : RuntimeException(errorMessage)