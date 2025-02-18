package com.lukaszwodniak.folky.controller

import com.lukaszwodniak.folky.annotations.endpointLogger.EndpointLogger
import com.lukaszwodniak.folky.handler.FilesHandler
import com.lukaszwodniak.folky.rest.files.specification.api.FilesApi
import org.springframework.core.io.Resource
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.multipart.MultipartFile

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
    override fun getImage(teamId: Long?, fileType: String?, filename: String?): ResponseEntity<Resource> {
        val resource = teamId?.let { filesHandler.handleGetImage(it, fileType!!, filename) }
        return if (resource != null) {
            ResponseEntity.ok()
                .header(
                    HttpHeaders.CONTENT_DISPOSITION,
                    "attachment; filename=${resource.filename}"
                )
                .contentType(
                    if (resource.filename?.contains(JPEG_EXTENSION) == true) MediaType.IMAGE_JPEG else MediaType.IMAGE_PNG
                )
                .body(resource)
        } else {
            ResponseEntity.notFound().build()
        }
    }


    @EndpointLogger
    override fun saveNewImage(teamId: Long?, fileType: String?, file: MultipartFile?): ResponseEntity<Void> {
        filesHandler.handleSaveImage(teamId!!, file!!, fileType!!)
        return ResponseEntity.ok().build()
    }

    @EndpointLogger
    override fun updateImage(teamId: Long?, fileType: String?, file: MultipartFile?): ResponseEntity<Void> {
        filesHandler.handleUpdateImage(teamId!!, file!!, fileType!!)
        return ResponseEntity.ok().build()
    }

    @EndpointLogger
    override fun deleteImage(teamId: Long?, filename: String?): ResponseEntity<Void> {
        filename?.let {
            filesHandler.handleDeleteImage(teamId!!, filename)
        }
        return ResponseEntity.ok().build()
    }

    /**
     * This method is exception, and it isn't described in openApi file to provide sending files as List<MultipartFile>
     *     instead of List<Resource>. For now, I don't know how to describe it 🥲
     */
    @EndpointLogger
    @PostMapping("/api/files/{teamId}/gallery")
    fun uploadFilesToGallery(
        @PathVariable teamId: Long?,
        @RequestParam("files") files: MutableList<MultipartFile>?
    ): ResponseEntity<Void> {
        files?.let {
            filesHandler.handleUploadGalleryImages(teamId!!, files)
        }
        return ResponseEntity.ok().build()
    }

    companion object {
        private const val PNG_EXTENSION = ".png"
        private const val JPEG_EXTENSION = ".jpg"
    }
}