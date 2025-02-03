package com.lukaszwodniak.folky.controller

import com.lukaszwodniak.folky.annotations.endpointLogger.EndpointLogger
import com.lukaszwodniak.folky.handler.FilesHandler
import com.lukaszwodniak.folky.rest.files.specification.api.FilesApi
import org.springframework.core.io.InputStreamResource
import org.springframework.core.io.Resource
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
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

    @EndpointLogger
    @GetMapping("/api/files/get-logo")
    fun getLogo(@RequestParam teamId: Long?): ResponseEntity<InputStreamResource?> {
        val resource = teamId?.let {
            filesHandler.handleGetLogo(it)
        }
        return if (resource != null) {
            ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=${resource.filename ?: "logo.png"}")
                .contentType(
                    if (resource.filename?.contains(JPEG_EXTENSION) == true) MediaType.IMAGE_JPEG else MediaType.IMAGE_PNG
                )
                .body(resource)
        } else {
            ResponseEntity.notFound().build()
        }
    }

    @EndpointLogger
    @GetMapping("/api/files/get-banner")
    fun getBanner(@RequestParam teamId: Long?): ResponseEntity<InputStreamResource?> {
        val resource = teamId?.let {
            filesHandler.handleGetBanner(it)
        }
        return if (resource != null) {
            ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=${resource.filename}")
                .contentType(
                    if (resource.filename?.contains(JPEG_EXTENSION) == true) MediaType.IMAGE_JPEG else MediaType.IMAGE_PNG
                )
                .body(resource)
        } else {
            ResponseEntity.notFound().build()
        }
    }

    @EndpointLogger
    @GetMapping("/api/files/get-image")
    fun getImage(@RequestParam teamId: Long?, @RequestParam filename: String): ResponseEntity<InputStreamResource?> {
        val resource = teamId?.let {
            filesHandler.handleGetImage(it, filename)
        }
        return if (resource != null) {
            ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=${resource.filename}")
                .contentType(
                    if (resource.filename?.contains(JPEG_EXTENSION) == true) MediaType.IMAGE_JPEG else MediaType.IMAGE_PNG
                )
                .body(resource)
        } else {
            ResponseEntity.notFound().build()
        }
    }

    @EndpointLogger
    override fun getGalleryUrls(teamId: Long?): ResponseEntity<MutableList<String>> {
       val fileNames = teamId?.let { filesHandler.handleGetGalleryImages(it) }
        return ResponseEntity.ok().body(fileNames)
    }

    companion object {
        private const val PNG_EXTENSION = ".png"
        private const val JPEG_EXTENSION = ".jpg"
    }
}