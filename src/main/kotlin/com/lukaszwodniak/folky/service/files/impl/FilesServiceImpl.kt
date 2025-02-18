package com.lukaszwodniak.folky.service.files.impl

import com.lukaszwodniak.folky.enums.FileType
import com.lukaszwodniak.folky.model.DancingTeam
import com.lukaszwodniak.folky.repository.DancingTeamRepository
import com.lukaszwodniak.folky.service.files.FilesService
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.core.io.Resource
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import java.io.File
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths
import java.util.*
import kotlin.io.path.deleteExisting

/**
 * FilesServiceImpl
 *
 * Created on: 2024-10-14
 * @author Łukasz Wodniak
 */

@Service
class FilesServiceImpl(
    private val dancingTeamRepository: DancingTeamRepository,
) : FilesService {

    private val filesUploadDirectory: Path =
        Paths.get(UPLOADS_DIRECTORY + File.separator)

    init {
        if (!Files.exists(filesUploadDirectory)) {
            Files.createDirectories(filesUploadDirectory)
        }
    }

    override fun getFile(filename: String): Resource {
        TODO("Not yet implemented")
    }

    override fun getFilesList(): List<String> {
        try {
            return Files.list(filesUploadDirectory).map { it.fileName.toString() }.toList()
        } catch (ex: Exception) {
            logger.error("Error on getting files list. Reason: ${ex.message}")
            return emptyList()
        }
    }

    override fun getTeamFilesList(dancingTeam: DancingTeam): List<String> {
        try {
            return Files.list(Paths.get("${filesUploadDirectory}${dancingTeam.filesUUID}"))
                .map { it.fileName.toString() }.toList()
        } catch (ex: Exception) {
            logger.error("Error on getting team files. Reason: ${ex.message}")
            return emptyList()
        }
    }

    override fun saveImage(dancingTeam: DancingTeam, file: MultipartFile, fileType: FileType) {
        val fileName = file.originalFilename
        val path = Paths.get("${UPLOADS_DIRECTORY}${File.separator}${dancingTeam.filesUUID}${File.separator}$fileName")
        try {
            Files.createDirectories(path.parent)
            file.transferTo(path)
            assignFilesDataInTeam(fileType, dancingTeam, fileName)
        } catch (exception: Exception) {
            logger.error("Error during saving image. Reason: ${exception.localizedMessage}")
        }
    }

    override fun updateImage(dancingTeam: DancingTeam, file: MultipartFile, fileType: FileType) {
        val fileName = file.originalFilename
        val existingFileName: String? = when (fileType) {
            FileType.LOGO -> dancingTeam.logoFilename
            FileType.BANNER -> dancingTeam.bannerFilename
            else -> null
        }
        try {
            existingFileName?.let {
                val existingPath =
                    Paths.get("${UPLOADS_DIRECTORY}${File.separator}${dancingTeam.filesUUID}${File.separator}$existingFileName")
                deleteExistingFile(existingPath)
            }
            val path =
                Paths.get("${UPLOADS_DIRECTORY}${File.separator}${dancingTeam.filesUUID}${File.separator}$fileName")
            Files.createDirectories(path.parent)
            file.transferTo(path)
            assignFilesDataInTeam(fileType, dancingTeam, fileName)
        } catch (exception: Exception) {
            logger.error("Error during updating image. Reason: ${exception.localizedMessage}")
        }
    }

    private fun assignFilesDataInTeam(
        fileType: FileType,
        dancingTeam: DancingTeam,
        fileName: String?
    ) {
        when (fileType) {
            FileType.LOGO -> dancingTeam.logoFilename = fileName
            FileType.BANNER -> dancingTeam.bannerFilename = fileName
            else -> {}
        }
        dancingTeamRepository.saveAndFlush(dancingTeam)
    }

    private fun deleteExistingFile(path: Path) {
        path.deleteExisting()
    }

    companion object {
        const val UPLOADS_DIRECTORY: String = "storage"
        const val IMAGES_DIRECTORY: String = "images"
        val logger: Logger = LoggerFactory.getLogger(this::class.java)

        fun generateTeamDirectory(): UUID {
            val dancingTeamDirUUID = UUID.randomUUID()
            val path = UPLOADS_DIRECTORY + File.separator + dancingTeamDirUUID.toString()
            val teamDirectory = File(path)
            if (!teamDirectory.exists()) {
                teamDirectory.mkdirs()
            }
            return dancingTeamDirUUID
        }
    }
}