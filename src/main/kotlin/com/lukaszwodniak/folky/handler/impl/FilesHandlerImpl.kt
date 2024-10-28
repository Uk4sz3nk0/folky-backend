package com.lukaszwodniak.folky.handler.impl

import com.lukaszwodniak.folky.error.NoSuchDancingTeamException
import com.lukaszwodniak.folky.handler.FilesHandler
import com.lukaszwodniak.folky.mapper.UtilMapper
import com.lukaszwodniak.folky.repository.DancingTeamRepository
import com.lukaszwodniak.folky.service.files.FilesService
import org.springframework.core.io.Resource
import org.springframework.stereotype.Service

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
}