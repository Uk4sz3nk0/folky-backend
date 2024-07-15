package com.lukaszwodniak.folky.handler

import lombok.RequiredArgsConstructor
import lombok.extern.slf4j.Slf4j
import org.springframework.stereotype.Service

/**
 * HelloHandler
 * Created on: 2024-07-15
 *
 * @author Łukasz Wodniak (lukasz.wodniak@student.up.krakow.pl)
 */

@Slf4j
@RequiredArgsConstructor
interface HelloHandler {

    fun greetings(name: String?): String
}