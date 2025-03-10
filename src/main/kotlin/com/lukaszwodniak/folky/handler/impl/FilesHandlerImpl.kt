package com.lukaszwodniak.folky.handler.impl

import com.lukaszwodniak.folky.enums.FileType
import com.lukaszwodniak.folky.error.NoSuchDancingTeamException
import com.lukaszwodniak.folky.handler.FilesHandler
import com.lukaszwodniak.folky.repository.DancingTeamRepository
import com.lukaszwodniak.folky.service.files.FilesService
import com.lukaszwodniak.folky.utils.FileUtils
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
        return when (typeOfFile) {
            FileType.LOGO -> FileUtils.getLogo(dancingTeam)
            FileType.BANNER -> FileUtils.getBanner(dancingTeam)
            FileType.IMAGE -> filename?.let { FileUtils.getImage(dancingTeam, it) }
        }
    }

    override fun handleDeleteImage(teamId: Long, filename: String) {
        val dancingTeam = dancingTeamRepository.findById(teamId).orElseThrow { NoSuchDancingTeamException(teamId) }
        FileUtils.deleteFile(dancingTeam, filename)
    }

    override fun handleUploadGalleryImages(teamId: Long, files: MutableList<MultipartFile>) {
        val dancingTeam = dancingTeamRepository.findById(teamId).orElseThrow { NoSuchDancingTeamException(teamId) }
        FileUtils.saveFiles(dancingTeam, files)
    }
}