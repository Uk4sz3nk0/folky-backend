package com.lukaszwodniak.folky.controller

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

/**
 * HealthCheckController
 *
 * Created on: 2025-03-25
 * @author Łukasz Wodniak
 */

@RestController
class HealthCheckController {

    @GetMapping("/_ah/health")
    fun healthCheck(): ResponseEntity<String> {
        return ResponseEntity.ok("Healthy")
    }
}