package com.lukaszwodniak.folky.controller

import org.springframework.data.domain.Sort

/**
 * ControllerCommons
 *
 * Created on: 2025-05-17
 * @author Łukasz Wodniak
 */

interface ControllerCommons {

    companion object {
        const val DEFAULT_PAGE: Int = 0
        const val DEFAULT_PAGE_SIZE: Int = 10
        const val DEFAULT_SORT_COLUMN: String = "id"
        val DEFAULT_SORT_DIRECTION: Sort.Direction = Sort.Direction.DESC
    }
}