package com.lukaszwodniak.folky.error

/**
 * NoSuchMusicInstrumentTypeException
 */

class NoSuchMusicInstrumentTypeException(id: Long) : RuntimeException(buildMessage(id)) {

    companion object {
        fun buildMessage(id: Long): String = "Music instrument type with id = $id does not exists"
    }
}