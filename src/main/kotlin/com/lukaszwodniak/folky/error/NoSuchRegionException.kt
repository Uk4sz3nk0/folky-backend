package com.lukaszwodniak.folky.error

/**
 * NoSuchRegionException
 * <br><br>
 * Created on: 2024-09-05
 * @author Łukasz Wodniak
 */

class NoSuchRegionException(id: Long) : RuntimeException(buildMessage(id)) {

    companion object {

        fun buildMessage(id: Long): String {
            return "Region with id = $id does not exists"
        }
    }
}