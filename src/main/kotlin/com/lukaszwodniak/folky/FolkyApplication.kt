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
*  - Dodać zespół i podstawową logikę - 0 prio
   - Dodać cloud messaging dla mobilek i ew. emaile
   - Dodać flagę - czy otwarta rekrutacja czy nie 1 - prio
   - Dodać requesty, że jeśli tak to że użytkownik może wysłać zapytanie o dołączenie do zespołu 2 -prio
   - Dodać powiązanie - userzy - zespoły (w jakim kto tańczy)
   - Dodać zaproszenia do zespołu (przeciwieństwo dodawania bo tu zespół zaprasza)
   - Dodać wszelkie operacje CURD dla stworzonych obiektów i cały flow (taki podstawowy chociaż)
*  - Dodać tworzenie blogów i wgl ??? (może ver 2?)
*  - Rozwiązać wszelkie TODO
*  - Dodać testy
* */
