package com.lukaszwodniak.folky.handler

import org.springframework.core.io.Resource

/**
 * FilesHandler
 * <br><br>
 * Created on: 2024-10-14
 * @author Łukasz Wodniak
 */

interface FilesHandler {

    fun handleGetFile(filename: String): Resource
    fun handleGetFilesList(): MutableList<String>
    fun handleGetTeamFiles(teamId: Long): MutableList<String>
    fun handleUploadFiles(teamId: Long, files: MutableList<Resource>)
}