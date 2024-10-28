package com.lukaszwodniak.folky.service.storage.impl

import com.lukaszwodniak.folky.enums.StoredFileType
import com.lukaszwodniak.folky.service.storage.StorageService
import lombok.RequiredArgsConstructor
import lombok.extern.slf4j.Slf4j
import org.springframework.core.io.FileSystemResource
import org.springframework.core.io.Resource
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import java.io.File
import java.nio.file.Files
import java.nio.file.Paths
import java.util.*

/**
 * StorageServiceImpl
 * Created on: 2024-07-29
 * @author Łukasz Wodniak
 */


@RequiredArgsConstructor
@Service
class StorageServiceImpl : StorageService {

    init {
        val directory = File(STORAGE_DIRECTORY)
        if (!directory.exists()) {
            directory.mkdirs()
        }
    }

    override fun storeFile(fileType: StoredFileType, teamUUID: UUID, file: MultipartFile) {
        // TODO: Implement handling exceptions
        val dir = File(STORAGE_DIRECTORY + File.separator + teamUUID.toString() + File.separator + getStorageDirByType(fileType))
        if (!dir.exists()) {
            dir.mkdirs()
        }
        val filePath = Paths.get(
            dir.path,
            file.originalFilename
        )
        file.inputStream.use { inputStream ->
            Files.copy(inputStream, filePath)
        }
    }

    override fun readFile(fileType: StoredFileType, teamUUID: UUID, filename: String): Resource {
        // TODO: Implement handling exceptions
        val filePath = Paths.get(
            STORAGE_DIRECTORY + File.separator + teamUUID.toString() + File.separator + getStorageDirByType(fileType),
            filename
        )
        return FileSystemResource(filePath.toFile())
    }

    private fun getStorageDirByType(fileType: StoredFileType): String {
        return when (fileType) {
            StoredFileType.DANCE_TEAM_ICON -> "icon"
            StoredFileType.DANCE_TEAM_GALLERY -> "gallery" // Main team gallery
        }
    }

    companion object {
        const val STORAGE_DIRECTORY = "storage"
    }
}