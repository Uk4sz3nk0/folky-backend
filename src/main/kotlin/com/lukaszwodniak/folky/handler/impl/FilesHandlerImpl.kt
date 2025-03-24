package com.lukaszwodniak.folky.handler.impl

import com.lukaszwodniak.folky.enums.FileType
import com.lukaszwodniak.folky.error.NoSuchDancingTeamException
import com.lukaszwodniak.folky.handler.FilesHandler
import com.lukaszwodniak.folky.repository.DancingTeamRepository
import com.lukaszwodniak.folky.service.files.FilesService
import org.springframework.core.io.Resource
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile

/**
 * FilesHandler
 *
 * Created on: 2024-10-14
 * @author Łukasz Wodniak
 */

@Service
class FilesHandlerImpl(
    val filesService: FilesService,
    val dancingTeamRepository: DancingTeamRepository
) : FilesHandler {

    override fun handleSaveImage(teamId: Long, file: MultipartFile, fileType: String) {
        val team = dancingTeamRepository.findById(teamId).orElseThrow { NoSuchDancingTeamException(teamId) }
        val typeOfFile = FileType.fromValue(fileType)
        filesService.saveImage(team, file, typeOfFile)
    }

    override fun handleUpdateImage(teamId: Long, file: MultipartFile, fileType: String) {
        val team = dancingTeamRepository.findById(teamId).orElseThrow { NoSuchDancingTeamException(teamId) }
        val typeOfFile = FileType.fromValue(fileType)
        filesService.updateImage(team, file, typeOfFile)
    }

    override fun handleGetImage(teamId: Long, fileType: String, filename: String?): Resource? {
        val typeOfFile = FileType.fromValue(fileType)
        val dancingTeam = dancingTeamRepository.findById(teamId).orElseThrow { NoSuchDancingTeamException(teamId) }
        val mappedFilename: String? = when (typeOfFile) {
            FileType.LOGO -> dancingTeam.logoFilename
            FileType.BANNER -> dancingTeam.bannerFilename
            FileType.IMAGE -> filename
        }

        return mappedFilename?.let { filesService.getImageFile(dancingTeam.filesUUID, it) }
    }

    override fun handleDeleteImage(teamId: Long, filename: String) {
        val dancingTeam = dancingTeamRepository.findById(teamId).orElseThrow { NoSuchDancingTeamException(teamId) }
        filesService.deleteFile(dancingTeam.filesUUID, filename)
    }

    override fun handleUploadGalleryImages(teamId: Long, files: MutableList<MultipartFile>) {
        val dancingTeam = dancingTeamRepository.findById(teamId).orElseThrow { NoSuchDancingTeamException(teamId) }
        filesService.saveFiles(dancingTeam.filesUUID, files)
    }
}