package com.lukaszwodniak.folky.handler.impl

import com.lukaszwodniak.folky.error.NoSuchDancingTeamException
import com.lukaszwodniak.folky.handler.FilesHandler
import com.lukaszwodniak.folky.mapper.UtilMapper
import com.lukaszwodniak.folky.repository.DancingTeamRepository
import com.lukaszwodniak.folky.service.files.FilesService
import com.lukaszwodniak.folky.utils.FileUtils
import org.springframework.core.io.InputStreamResource
import org.springframework.core.io.Resource
import org.springframework.stereotype.Service
import java.io.File
import java.io.FileInputStream

/**
 * FilesHandler
 * <br><br>
 * Created on: 2024-10-14
 * @author Łukasz Wodniak
 */

@Service
class FilesHandlerImpl(
    val filesService: FilesService,
    val dancingTeamRepository: DancingTeamRepository
) : FilesHandler {

    override fun handleGetFile(filename: String): Resource {
        return filesService.getFile(filename)
    }

    override fun handleGetFilesList(): MutableList<String> {
        return UtilMapper.INSTANCE.mapStrings(filesService.getFilesList())
    }

    override fun handleGetTeamFiles(teamId: Long): MutableList<String> {
        val dancingTeam = dancingTeamRepository.findById(teamId).orElseThrow { NoSuchDancingTeamException(teamId) }
        return UtilMapper.INSTANCE.mapStrings(filesService.getTeamFilesList(dancingTeam))
    }

    override fun handleUploadFiles(teamId: Long, files: MutableList<Resource>) {
        val dancingTeam = dancingTeamRepository.findById(teamId).orElseThrow { NoSuchDancingTeamException(teamId) }
        filesService.uploadFiles(dancingTeam, files)
    }

    override fun handleGetLogo(teamId: Long): InputStreamResource? {
        val dancingTeam = dancingTeamRepository.findById(teamId).orElseThrow { NoSuchDancingTeamException(teamId) }
        return FileUtils.getLogo(dancingTeam)
    }

    override fun handleGetBanner(teamId: Long): InputStreamResource? {
        val dancingTeam = dancingTeamRepository.findById(teamId).orElseThrow { NoSuchDancingTeamException(teamId) }
        return FileUtils.getBanner(dancingTeam)
    }

    override fun handleGetGalleryImages(teamId: Long): MutableList<String> {
        val dancingTeam = dancingTeamRepository.findById(teamId).orElseThrow { NoSuchDancingTeamException(teamId) }
        return FileUtils.getGalleryUrls(dancingTeam)
    }

    override fun handleGetImage(teamId: Long, filename: String): InputStreamResource? {
        val dancingTeam = dancingTeamRepository.findById(teamId).orElseThrow { NoSuchDancingTeamException(teamId) }
        return FileUtils.getImage(dancingTeam, filename)
    }
}