package com.lukaszwodniak.folky.utils

import com.lukaszwodniak.folky.model.DancingTeam
import org.slf4j.LoggerFactory
import org.springframework.core.io.InputStreamResource
import java.io.File
import java.io.FileInputStream
import java.util.*

/**
 * FileUtils
 *
 * Created on: 2025-01-12
 * @author Łukasz Wodniak
 */

class FileUtils {

    companion object {
        private val logger = LoggerFactory.getLogger(FileUtils::class.java)

        fun getLogo(team: DancingTeam): InputStreamResource? = loadFileAsInputStream(team.filesUUID, team.logoFilename)

        fun getBanner(team: DancingTeam): InputStreamResource? =
            loadFileAsInputStream(team.filesUUID, team.bannerFilename)

        fun getGalleryUrls(team: DancingTeam): MutableList<String> = loadGalleryUrls(team)
        fun getImage(dancingTeam: DancingTeam, fileName: String): InputStreamResource? =
            getImage(dancingTeam.filesUUID, fileName)

        private fun loadFileAsInputStream(directoryUUID: UUID, fileName: String?): InputStreamResource? {
            try {
                val file = File("storage/$directoryUUID/$fileName")
                if (!file.exists()) {
                    return null
                }
                val inputStream = FileInputStream(file)
                return InputStreamResource(inputStream)
            } catch (exception: Exception) {
                logger.error("Error during loading file \"$fileName\". Reason: ${exception.localizedMessage}")
            }
            return null
        }

        private fun loadGalleryUrls(team: DancingTeam): MutableList<String> {
            try {
                val teamDirectory = File("storage/${team.filesUUID}")
                if (teamDirectory.exists() && teamDirectory.isDirectory) {
                    val files = teamDirectory.listFiles()?.map {
                        it.name.replace("storage/${team.filesUUID}/", "")
                    }
                    val filteredFiles = files?.filter { it != team.logoFilename && it != team.bannerFilename }
                    return filteredFiles.orEmpty().toMutableList()
                }
            } catch (exception: Exception) {
                logger.error("Error during loading gallery urls. Reason: ${exception.localizedMessage}")
            }
            return ArrayList()
        }

        private fun getImage(directoryUUID: UUID, fileName: String): InputStreamResource? {
            try {
                val file = File("storage/$directoryUUID/$fileName")
                if (file.exists()) {
                    val inputStream = FileInputStream(file)
                    return InputStreamResource(inputStream)
                }
            } catch (exception: Exception) {
                logger.error("Error during loading image $directoryUUID. Reason: ${exception.localizedMessage}")
            }
            return null
        }
    }
}