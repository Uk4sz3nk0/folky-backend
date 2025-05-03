package com.lukaszwodniak.folky.error

/**
 * NoSuchAddressException
 *
 * Created on: 2025-05-03
 * @author Łukasz Wodniak
 */

class NoSuchAddressException(id: Long) : NoSuchElementException(mapMessage(id)) {

    companion object {
        fun mapMessage(id: Long): String {
            return "No such address with id = $id"
        }
    }
}