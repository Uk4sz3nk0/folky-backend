package com.lukaszwodniak.folky.error

/**
 * NoSuchRegionException
 *
 * Created on: 2024-09-05
 * @author Łukasz Wodniak
 */

class NoSuchRegionException(id: Long) : RuntimeException(buildMessage(id)) {

    companion object {
        fun buildMessage(id: Long): String = "Region with id = $id does not exists"
    }
}