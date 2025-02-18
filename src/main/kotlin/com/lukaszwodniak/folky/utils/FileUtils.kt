package com.lukaszwodniak.folky.utils

import com.lukaszwodniak.folky.model.DancingTeam
import org.slf4j.LoggerFactory
import org.springframework.core.io.InputStreamResource
import org.springframework.web.multipart.MultipartFile
import java.io.File
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths
import java.nio.file.StandardCopyOption
import java.util.*
import kotlin.io.path.deleteExisting
import kotlin.io.path.exists

/**
 * FileUtils
 *
 * Created on: 2025-01-12
 * @author Łukasz Wodniak
 */

class FileUtils {

    companion object {
        private const val UPLOADS_DIRECTORY: String = "storage"
        private val logger = LoggerFactory.getLogger(FileUtils::class.java)

        fun getLogo(team: DancingTeam): InputStreamResource? = loadFileAsInputStream(team.filesUUID, team.logoFilename)

        fun getBanner(team: DancingTeam): InputStreamResource? =
            loadFileAsInputStream(team.filesUUID, team.bannerFilename)

        fun getGalleryUrls(team: DancingTeam): MutableList<String> = loadGalleryUrls(team)
        fun getImage(dancingTeam: DancingTeam, fileName: String): InputStreamResource? =
            getFile(dancingTeam.filesUUID, fileName)

        fun deleteFile(dancingTeam: DancingTeam, fileName: String) {
            val path = getBaseUploadPath(dancingTeam.filesUUID, fileName)
            if (path.exists()) {
                path.deleteExisting()
            }
        }

        fun saveFiles(dancingTeam: DancingTeam, files: MutableList<MultipartFile>) {
            val basePath = "${UPLOADS_DIRECTORY}${File.separator}${dancingTeam.filesUUID}"
            try {
                files.forEach {
                    val path = Paths.get("$basePath${File.separator}${it.originalFilename}")
                    Files.copy(it.inputStream, path, StandardCopyOption.REPLACE_EXISTING)
                }
            } catch (exception: Exception) {
                logger.error("Error during saving gallery images. Reason: ${exception.localizedMessage}")
            }
        }

        private fun loadFileAsInputStream(directoryUUID: UUID, fileName: String?): InputStreamResource? {
            try {
                val file = getBaseUploadPath(directoryUUID, fileName!!).toFile()
                if (!file.exists()) {
                    return null
                }
                return InputStreamResource(file.inputStream())
            } catch (exception: Exception) {
                logger.error("Error during loading file \"$fileName\". Reason: ${exception.localizedMessage}")
            }
            return null
        }

        private fun loadGalleryUrls(team: DancingTeam): MutableList<String> {
            try {
                val teamDirectory = File("${UPLOADS_DIRECTORY}${File.separator}${team.filesUUID}")
                if (teamDirectory.exists() && teamDirectory.isDirectory) {
                    val files = teamDirectory.listFiles()?.map {
                        it.name.replace("${UPLOADS_DIRECTORY}${File.separator}${team.filesUUID}${File.separator}", "")
                    }
                    val filteredFiles = files?.filter { it != team.logoFilename && it != team.bannerFilename }
                    return filteredFiles.orEmpty().toMutableList()
                }
            } catch (exception: Exception) {
                logger.error("Error during loading gallery urls. Reason: ${exception.localizedMessage}")
            }
            return ArrayList()
        }

        private fun getFile(directoryUUID: UUID, fileName: String): InputStreamResource? {
            try {
                val file = getBaseUploadPath(directoryUUID, fileName).toFile()
                if (file.exists()) {
                    return InputStreamResource(file.inputStream())
                }
            } catch (exception: Exception) {
                logger.error("Error during loading image $directoryUUID. Reason: ${exception.localizedMessage}")
            }
            return null
        }

        private fun getBaseUploadPath(filesUUID: UUID, fileName: String): Path =
            Paths.get("${UPLOADS_DIRECTORY}${File.separator}$filesUUID${File.separator}$fileName")
    }
}