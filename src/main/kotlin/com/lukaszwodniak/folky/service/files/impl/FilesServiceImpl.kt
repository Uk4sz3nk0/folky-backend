package com.lukaszwodniak.folky.service.files.impl

import com.lukaszwodniak.folky.model.DancingTeam
import com.lukaszwodniak.folky.service.files.FilesService
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.core.io.Resource
import org.springframework.stereotype.Service
import java.io.File
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths
import java.util.*

/**
 * FilesServiceImpl
 *
 * Created on: 2024-10-14
 * @author Łukasz Wodniak
 */

@Service
class FilesServiceImpl : FilesService {

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

    override fun uploadFiles(team: DancingTeam, files: List<Resource>) {
        try {
            // TODO: Maybe in future, set enum based getting directory in Images directory place
            files.forEach { file ->
                val path = "${team.filesUUID}${File.separator}${IMAGES_DIRECTORY}${File.separator}${file.filename}"
                val targetPath =
                    filesUploadDirectory.resolve(path)
                Files.copy(file.inputStream, targetPath)
            }
        } catch (exception: Exception) {
            logger.error("Error during uploading files. Reason ${exception.message}")
        }
    }

    override fun generateTeamDirectory(): UUID {
        val dancingTeamDirUUID = UUID.randomUUID()
        val path = UPLOADS_DIRECTORY + File.separator + dancingTeamDirUUID.toString()
        val teamDirectory = File(path)
        if (!teamDirectory.exists()) {
            teamDirectory.mkdirs()
        }
        return dancingTeamDirUUID
    }

    companion object {
        const val UPLOADS_DIRECTORY: String = "upload-directory"
        const val IMAGES_DIRECTORY: String = "images"
        val logger: Logger = LoggerFactory.getLogger(this::class.java)
    }
}