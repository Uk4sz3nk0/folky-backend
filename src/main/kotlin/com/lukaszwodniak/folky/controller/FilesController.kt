package com.lukaszwodniak.folky.controller

import com.lukaszwodniak.folky.annotations.endpointLogger.EndpointLogger
import com.lukaszwodniak.folky.handler.FilesHandler
import com.lukaszwodniak.folky.rest.files.specification.api.FilesApi
import lombok.extern.slf4j.Slf4j
import org.springframework.core.io.Resource
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RestController

/**
 * FilesController
 *
 * Created on: 2024-10-13
 * @author Łukasz Wodniak
 */

@RestController
class FilesController(
    val filesHandler: FilesHandler
) : FilesApi {

    @EndpointLogger
    override fun getFile(filename: String?): ResponseEntity<Resource> {
        return ResponseEntity.ok(filename?.let { filesHandler.handleGetFile(it) })
    }

    @EndpointLogger
    override fun getFilesList(): ResponseEntity<MutableList<String>> {
        return ResponseEntity.ok(filesHandler.handleGetFilesList())
    }

    @EndpointLogger
    override fun getTeamFiles(teamId: Long?): ResponseEntity<MutableList<String>> {
        return ResponseEntity.ok(teamId?.let { filesHandler.handleGetTeamFiles(it) })
    }

    @EndpointLogger
    override fun uploadFiles(teamId: Long?, files: MutableList<Resource>?): ResponseEntity<Void> {
        files?.let { filesList ->
            teamId?.let { teamId -> filesHandler.handleUploadFiles(teamId, filesList) }
        }
        return ResponseEntity.ok(null)
    }
}