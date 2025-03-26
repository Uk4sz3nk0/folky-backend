package com.lukaszwodniak.folky.handler

import org.springframework.core.io.Resource
import org.springframework.web.multipart.MultipartFile

/**
 * FilesHandler
 *
 * Created on: 2024-10-14
 * @author Łukasz Wodniak
 */

interface FilesHandler {

    fun handleSaveImage(teamId: Long, file: MultipartFile, fileType: String)
    fun handleUpdateImage(teamId: Long, file: MultipartFile, fileType: String)
    fun handleGetImage(teamId: Long, fileType: String, filename: String?): Resource?
    fun handleDeleteImage(teamId: Long, filename: String)
    fun handleUploadGalleryImages(teamId: Long, files: MutableList<MultipartFile>)
}