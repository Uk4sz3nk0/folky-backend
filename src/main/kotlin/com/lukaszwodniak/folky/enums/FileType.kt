package com.lukaszwodniak.folky.enums

/**
 * FileType
 *
 * Created on: 2025-02-21
 * @author Łukasz Wodniak
 */

enum class FileType(val type: String) {
    LOGO("logo"),
    BANNER("banner"),
    IMAGE("image");

    companion object {
        fun fromValue(value: String): FileType {
            return entries.firstOrNull { it.type.equals(value, ignoreCase = true) }
                ?: throw IllegalArgumentException("Invalid fileType: $value")
        }
    }
}