package com.lukaszwodniak.folky

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.ConfigurationPropertiesScan
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.runApplication

@SpringBootApplication
class FolkyApplication

fun main(args: Array<String>) {
    runApplication<FolkyApplication>(*args)
}

/*
* TODO LIST (nie do końca się już tym sugerować ale wciąż mieć na uwadze):
   - Dodać cloud messaging dla mobilek i ew. emaile (ver 2)
   - Dodać tworzenie blogów i wgl ??? (może ver 2?)
   - Rozwiązać wszelkie TODO
   - Dodać testy
* */
