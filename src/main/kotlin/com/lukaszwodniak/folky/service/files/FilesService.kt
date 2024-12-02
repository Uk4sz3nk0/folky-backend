package com.lukaszwodniak.folky.service.files

import com.lukaszwodniak.folky.model.DancingTeam
import org.springframework.core.io.Resource
import java.util.UUID

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
    fun uploadFiles(team: DancingTeam, files: List<Resource>)
    fun generateTeamDirectory(): UUID
}