package com.lukaszwodniak.folky.service.files

import com.lukaszwodniak.folky.enums.FileType
import com.lukaszwodniak.folky.model.DancingTeam
import org.springframework.core.io.InputStreamResource
import org.springframework.core.io.Resource
import org.springframework.web.multipart.MultipartFile
import java.util.UUID

/**
 * FilesService
 * <br><br>
 * Created on: 2024-10-14
 * @author Łukasz Wodniak
 */

interface FilesService {

    fun saveImage(dancingTeam: DancingTeam, file: MultipartFile, fileType: FileType)
    fun saveImage(filesUUID: UUID, file: MultipartFile, fileType: FileType)
    fun updateImage(dancingTeam: DancingTeam, file: MultipartFile, fileType: FileType)

    // Moving from FileUtils after adding Google Cloud storage

    // This method is unification of getLogo, getBanner, getImage
    fun getImageFile(filesUUID: UUID, filename: String): InputStreamResource?
    fun getGalleryUrls(dancingTeam: DancingTeam): MutableList<String>
    fun deleteFile(filesUUID: UUID, filename: String)
    fun deleteTeamDirectory(filesUUID: UUID)
    fun saveFiles(filesUUID: UUID, files: MutableList<MultipartFile>)
}