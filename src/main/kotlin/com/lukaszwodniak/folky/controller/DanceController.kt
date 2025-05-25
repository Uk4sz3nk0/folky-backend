package com.lukaszwodniak.folky.controller

import com.lukaszwodniak.folky.annotations.endpointLogger.EndpointLogger
import com.lukaszwodniak.folky.handler.DanceHandler
import com.lukaszwodniak.folky.rest.dance.specification.api.DanceApi
import com.lukaszwodniak.folky.rest.specification.models.DanceDto
import com.lukaszwodniak.folky.rest.specification.models.DanceRequestDto
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RestController

/**
 * DanceController
 *
 * Created on: 2024-08-22
 * @author Łukasz Wodniak
 */

@RestController
class DanceController(
    val danceHandler: DanceHandler
) : DanceApi {

    @EndpointLogger
    override fun setDance(danceRequest: DanceRequestDto?): ResponseEntity<DanceDto> {
        val addedDance = danceRequest?.let { danceHandler.handleAddDance(it) }
        return ResponseEntity.ok(addedDance)
    }

    @EndpointLogger
    override fun deleteDance(id: Long?): ResponseEntity<Void> {
        id?.let { danceHandler.handleDeleteDance(it) }
        return ResponseEntity.ok(null)
    }

    @EndpointLogger
    override fun getDanceById(id: Long?): ResponseEntity<DanceRequestDto> {
        val dance = id?.let { danceHandler.handleGetDanceById(it) }
        return ResponseEntity.ok(dance)
    }

    @EndpointLogger
    override fun getDances(locale: String?, phrase: String?): ResponseEntity<MutableList<DanceDto>> {
        val dances = locale?.let { danceHandler.handleGetDancesByLocale(it) }
            ?: phrase?.let { danceHandler.handleGetDancesByName(it) }
            ?: danceHandler.handleGetDances()
        return ResponseEntity.ok(dances.toMutableList())
    }
}