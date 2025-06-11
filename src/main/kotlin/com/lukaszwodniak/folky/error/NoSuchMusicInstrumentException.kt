package com.lukaszwodniak.folky.error

/**
 * NoSuchMusicInstrumentException
 *
 * Created on: 2024-08-28
 * @author Łukasz Wodniak
 */

class NoSuchMusicInstrumentException(id: Long) : RuntimeException(buildMessage(id)) {

    companion object {
        fun buildMessage(id: Long): String = "Music instrument with id  = $id does not exists"
    }
}