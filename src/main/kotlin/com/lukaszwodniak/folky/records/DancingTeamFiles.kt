package com.lukaszwodniak.folky.records

import org.springframework.web.multipart.MultipartFile

/**
 * DancingTeamFiles
 *
 * Created on: 2025-02-02
 * @author Łukasz Wodniak
 */

data class DancingTeamFiles(
    val logo: MultipartFile? = null,
    val banner: MultipartFile? = null,
)
