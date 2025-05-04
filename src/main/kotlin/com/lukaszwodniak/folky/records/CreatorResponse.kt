package com.lukaszwodniak.folky.records

import com.lukaszwodniak.folky.enums.UserType

/**
 * CreatorResponse
 *
 * Created on: 2025-05-12
 * @author Łukasz Wodniak
 */

data class CreatorResponse(
    val creatorType: UserType,
    val creator: Any,
    val id: Long
)
