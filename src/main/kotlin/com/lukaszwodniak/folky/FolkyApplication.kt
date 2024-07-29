package com.lukaszwodniak.folky

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class FolkyApplication

fun main(args: Array<String>) {
    runApplication<FolkyApplication>(*args)
}

/*
* TODO LIST:
*  - Dodać system plików do zapisu plików
*  - Dodać zespół i podstawową logikę
*  - Dodać wszelkie operacje CURD dla stworzonych obiektów i cały flow (taki podstawowy chociaż)
*  - Dodać tworzenie blogów i wgl
   - Dodać cloud messaging dla mobilek i ew. emaile
* */
