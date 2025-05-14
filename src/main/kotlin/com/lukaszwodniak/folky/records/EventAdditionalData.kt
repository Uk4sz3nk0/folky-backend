package com.lukaszwodniak.folky.records

/**
 * EventAdditionalData
 *
 * Created on: 2025-05-05
 * @author Łukasz Wodniak
 */

data class EventAdditionalData(
    val isFreeEntry: Boolean?,
    val isParticipantToo: Boolean?,
    val creatorId: Long?
)
