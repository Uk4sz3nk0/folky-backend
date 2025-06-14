package com.lukaszwodniak.folky.error

/**
 * DancingTeamExistsException
 *
 * Created on: 2024-09-05
 * @author Łukasz Wodniak
 */

class DancingTeamWithGivenNameExistsException(errorMessage: String) : RuntimeException(errorMessage)