package com.lukaszwodniak.folky.service.storage

import com.lukaszwodniak.folky.enums.StoredFileType
import org.springframework.core.io.Resource
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import java.util.*

/**
 * StorageService
 * Created on: 2024-07-29
 * @author Łukasz Wodniak
 */

@Service
interface StorageService {

    fun storeFile(fileType: StoredFileType, teamUUID: UUID, file: MultipartFile)
    fun readFile(fileType: StoredFileType, teamUUID: UUID, filename: String): Resource

    // TODO: Add blog galley
}