package com.lukaszwodniak.folky.records

/**
 * FilterObject
 *
 * Created on: 2025-03-17
 * @author Łukasz Wodniak
 */

data class FilterTeamsObject(
    val creationDate: Range? = null,
    val dancersAmount: Range? = null,
    val musiciansAmount: Range? = null,
    val selectedRegions: List<Long> = listOf(),
    val ownedSocialMedia: List<String> = listOf(),
)