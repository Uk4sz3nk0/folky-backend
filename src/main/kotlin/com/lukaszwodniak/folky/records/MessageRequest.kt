package com.lukaszwodniak.folky.records

/**
 * MessageRequest
 *
 * Created on: 2024-10-24
 * @author Łukasz Wodniak
 */

data class MessageRequest(
    var receivers: MutableList<String> = mutableListOf(),
    var title: String = "",
    var message: String = "",
    var additionalData: Map<String, String> = mutableMapOf()
)
