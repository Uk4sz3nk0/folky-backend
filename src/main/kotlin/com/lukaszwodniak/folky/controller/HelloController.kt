package com.lukaszwodniak.folky.controller

import com.lukaszwodniak.folky.handler.HelloHandler
import com.lukaszwodniak.folky.rest.hello.specification.api.HelloApi
import lombok.RequiredArgsConstructor
import lombok.extern.slf4j.Slf4j
import org.springframework.http.ResponseEntity

/**
 * HelloController - example test controller
 * Created on: 2024-07-15
 *
 * @author Łukasz Wodniak (lukasz.wodniak@student.up.krakow.pl)
 */

@Slf4j
@RequiredArgsConstructor
class HelloController(private val helloHandler: HelloHandler) : HelloApi {


    override fun helloWithGreetings(name: String?): ResponseEntity<String> {
        return ResponseEntity.ok(helloHandler.greetings(name))
    }
}