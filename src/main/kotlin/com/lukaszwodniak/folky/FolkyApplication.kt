package com.lukaszwodniak.folky

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class FolkyApplication

fun main(args: Array<String>) {
    runApplication<FolkyApplication>(*args)
}

/**
 * TODO
 * - Add tests
 * - Rewrite dance-specification
 * - Rewrite messaging-specification
 * - Rewrite music instrument specification
 * - Rewrite recruitment specification
 */
