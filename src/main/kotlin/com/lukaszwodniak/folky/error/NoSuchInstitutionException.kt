package com.lukaszwodniak.folky.error

/**
 * NoSuchInstitutionException
 *
 * Created on: 2025-05-04
 * @author Łukasz Wodniak
 */

class NoSuchInstitutionException(id: Long) : NoSuchElementException(toMessage(id)) {

    companion object {
        fun toMessage(id: Long): String = "No institution found for id: $id"
    }
}