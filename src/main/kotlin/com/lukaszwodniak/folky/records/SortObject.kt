package com.lukaszwodniak.folky.records

import org.springframework.data.domain.Sort

/**
 * Sort
 *
 * Created on: 2025-03-17
 * @author Łukasz Wodniak
 */

data class SortObject(
    val orderBy: String,
    val direction: Sort.Direction
)
