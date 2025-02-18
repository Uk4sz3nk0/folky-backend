package com.lukaszwodniak.folky.service.files

import com.lukaszwodniak.folky.enums.FileType
import com.lukaszwodniak.folky.model.DancingTeam
import org.springframework.core.io.Resource
import org.springframework.web.multipart.MultipartFile

/**
 * FilesService
 * <br><br>
 * Created on: 2024-10-14
 * @author Łukasz Wodniak
 */

interface FilesService {

    fun getFile(filename: String): Resource
    fun getFilesList(): List<String>
    fun getTeamFilesList(dancingTeam: DancingTeam): List<String>
    fun saveImage(dancingTeam: DancingTeam, file: MultipartFile, fileType: FileType)
    fun updateImage(dancingTeam: DancingTeam, file: MultipartFile, fileType: FileType)
}