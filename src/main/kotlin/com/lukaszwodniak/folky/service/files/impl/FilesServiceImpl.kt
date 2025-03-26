package com.lukaszwodniak.folky.service.files.impl

import com.google.cloud.storage.BlobId
import com.google.cloud.storage.BlobInfo
import com.google.cloud.storage.Storage
import com.google.cloud.storage.Storage.BlobListOption
import com.google.cloud.storage.StorageOptions
import com.lukaszwodniak.folky.enums.FileType
import com.lukaszwodniak.folky.model.DancingTeam
import com.lukaszwodniak.folky.repository.DancingTeamRepository
import com.lukaszwodniak.folky.service.files.FilesService
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.core.io.InputStreamResource
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import java.io.ByteArrayInputStream
import java.io.File
import java.util.*

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

    private val storage: Storage = StorageOptions.getDefaultInstance().service

    @Value("\${com.lukaszwodniak.folky.gcloud.storage.bucket}")
    private lateinit var bucketName: String

    override fun saveImage(dancingTeam: DancingTeam, file: MultipartFile, fileType: FileType) {
        val fileName = file.originalFilename
        try {
            saveImage(dancingTeam.filesUUID, file, fileType)
            assignFilesDataInTeam(fileType, dancingTeam, fileName)
        } catch (exception: Exception) {
            logger.error("Error during saving image with assigning data. Reason: ${exception.localizedMessage}")
        }
    }

    override fun saveImage(filesUUID: UUID, file: MultipartFile, fileType: FileType) {
        val fileName = file.originalFilename ?: throw IllegalArgumentException("File name cannot be null")
        val path = "$filesUUID/$fileName"
        val blobId = BlobId.of(bucketName, path)
        val blobInfo = BlobInfo.newBuilder(blobId).setContentType(file.contentType).build()

        try {
            storage.create(blobInfo, file.inputStream)
        } catch (exception: Exception) {
            logger.error("Error saving file $fileName. Reason: ${exception.localizedMessage}")
            throw exception
        }
    }

    override fun updateImage(dancingTeam: DancingTeam, file: MultipartFile, fileType: FileType) {
        val existingFileName: String? = when (fileType) {
            FileType.LOGO -> dancingTeam.logoFilename
            FileType.BANNER -> dancingTeam.bannerFilename
            else -> null
        }
        try {
            existingFileName?.let { deleteFile(dancingTeam.filesUUID, it) }
            saveImage(dancingTeam, file, fileType)
        } catch (exception: Exception) {
            logger.error("Error during updating image. Reason: ${exception.localizedMessage}")
        }
    }

    override fun getImageFile(filesUUID: UUID, filename: String): InputStreamResource? {
        return try {
            val path = "$filesUUID/$filename"
            val blob = storage.get(bucketName, path)
            if (blob != null && blob.exists()) {
                val content = blob.getContent()
                InputStreamResource(ByteArrayInputStream(content))
            } else {
                null
            }
        } catch (exception: Exception) {
            logger.error("Error during getting image. Reason: ${exception.localizedMessage}")
            null
        }
    }

    override fun getGalleryUrls(dancingTeam: DancingTeam): MutableList<String> {
        val allFiles = listFiles(dancingTeam.filesUUID)
        val fileNamesFilters: MutableList<String> = mutableListOf()
        dancingTeam.logoFilename?.let { fileNamesFilters.add(it) }
        dancingTeam.bannerFilename?.let { fileNamesFilters.add(it) }

        return allFiles.filter { !fileNamesFilters.contains(it) }.toMutableList()
    }

    override fun deleteFile(filesUUID: UUID, filename: String) {
        val path = "$filesUUID/$filename"
        try {
            val blobId: BlobId = BlobId.of(bucketName, path)
            val blob = storage.get(blobId)

            blob.delete()
        } catch (exception: Exception) {
            logger.error("Error during deleting file $filename. Reason: ${exception.localizedMessage}")
        }
    }

    override fun deleteTeamDirectory(filesUUID: UUID) {
        try {
            val blobs = storage.list(bucketName, BlobListOption.prefix("$filesUUID/"))
            blobs.iterateAll().forEach { it.delete() }
        } catch (exception: Exception) {
            logger.error("Error during deleting team directory. Reason: ${exception.localizedMessage}")
        }
    }

    override fun saveFiles(filesUUID: UUID, files: MutableList<MultipartFile>) {
        try {
            // TODO: Check is proper file type
            files.forEach { saveImage(filesUUID, it, FileType.IMAGE) }
        } catch (exception: Exception) {
            logger.error("Error during saving files. Reason: ${exception.localizedMessage}")
        }
    }

    private fun listFiles(filesUUID: UUID): MutableList<String> {
        val files = storage.list(
            bucketName, BlobListOption.prefix("$filesUUID/")
        ).iterateAll().map { it.name.replace("$filesUUID/", "") }.toMutableList()
        logger.info("FILES of $filesUUID => ${files.joinToString("\n")}")
        return files
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

    companion object {
        val logger: Logger = LoggerFactory.getLogger(this::class.java)
    }
}