package com.lukaszwodniak.folky.handler.impl

import com.lukaszwodniak.folky.handler.HelloHandler
import org.springframework.stereotype.Service

/**
 * HelloHandlerImpl
 * Created on: 2024-07-15
 *
 * @author Łukasz Wodniak (lukasz.wodniak@student.up.krakow.pl)
 */

@Service
class HelloHandlerImpl : HelloHandler {

    override fun greetings(name: String?): String {
        return "Hello $name"
    }
}