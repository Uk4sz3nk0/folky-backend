package com.lukaszwodniak.folky.error

/**
 * NoSuchDancingTeamException
 *
 * Created on: 2024-08-20
 * @author Łukasz Wodniak
 */

class NoSuchDancingTeamException(id: Long) : RuntimeException(buildErrorMessage(id)) {

    companion object {

        private fun buildErrorMessage(id: Long): String {
            return "Session with id = $id not exists"
        }
    }
}