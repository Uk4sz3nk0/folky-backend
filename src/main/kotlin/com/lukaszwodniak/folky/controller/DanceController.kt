package com.lukaszwodniak.folky.controller

import com.lukaszwodniak.folky.annotations.endpointLogger.EndpointLogger
import com.lukaszwodniak.folky.handler.DanceHandler
import com.lukaszwodniak.folky.rest.dance.specification.api.DanceApi
import com.lukaszwodniak.folky.rest.specification.models.DanceDto
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
    override fun addDance(dance: DanceDto?): ResponseEntity<DanceDto> {
        val addedDance = dance?.let { danceHandler.handleAddDance(it) }
        return ResponseEntity.ok(addedDance)
    }

    @EndpointLogger
    override fun deleteDance(id: Long?): ResponseEntity<Void> {
        return ResponseEntity.ok(null)
    }

    @EndpointLogger
    override fun getDanceById(id: Long?): ResponseEntity<DanceDto> {
        val dance = id?.let { danceHandler.handleGetDanceById(it) }
        return ResponseEntity.ok(dance)
    }

    @EndpointLogger
    override fun getDances(): ResponseEntity<MutableList<DanceDto>> {
        return ResponseEntity.ok(danceHandler.handleGetDances())
    }

    @EndpointLogger
    override fun getDancesByLocale(locale: String?): ResponseEntity<MutableList<DanceDto>> {
        val dances = locale?.let { danceHandler.handleGetDancesByLocale(it) }
        return ResponseEntity.ok(dances)
    }

    @EndpointLogger
    override fun getDancesByName(phrase: String?): ResponseEntity<MutableList<DanceDto>> {
        val dances = phrase?.let { danceHandler.handleGetDancesByName(it) }
        return ResponseEntity.ok(dances)
    }

    @EndpointLogger
    override fun updateDance(dance: DanceDto?): ResponseEntity<DanceDto> {
        val updatedDance = dance?.let { danceHandler.handleUpdateDance(it) }
        return ResponseEntity.ok(updatedDance)
    }
}