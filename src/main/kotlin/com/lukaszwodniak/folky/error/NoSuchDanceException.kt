package com.lukaszwodniak.folky.error

/**
 * NoSuchDanceException
 *
 * Created on: 2024-08-27
 * @author Łukasz Wodniak
 */

class NoSuchDanceException(id: Long) : RuntimeException(buildMessage(id)) {

    companion object {

        fun buildMessage(id: Long): String {
            return "Dance with id = $id does not exists"
        }
    }
}