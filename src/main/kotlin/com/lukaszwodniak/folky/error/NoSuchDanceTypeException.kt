package com.lukaszwodniak.folky.error

/**
 * NoSuchDanceTypeException
 *
 * Created on: 2024-08-27
 * @author Łukasz Wodniak
 */

class NoSuchDanceTypeException(id: Long) : RuntimeException(buildMessage(id)) {

    companion object {
        fun buildMessage(id: Long): String = "Dance type with id = $id does not exists"
    }
}