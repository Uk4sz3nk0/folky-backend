package com.lukaszwodniak.folky

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class FolkyApplication

fun main(args: Array<String>) {
    runApplication<FolkyApplication>(*args)
}

/*
* TODO LIST (nie do końca się już tym sugerować ale wciąż mieć na uwadze):
*  - Dodać zespół i podstawową logikę - 0 prio
   - Dodać cloud messaging dla mobilek i ew. emaile (ver 2)
   - Dodać flagę - czy otwarta rekrutacja czy nie 1 - prio
   - Dodać requesty, że jeśli tak to że użytkownik może wysłać zapytanie o dołączenie do zespołu 2 -prio
   - Dodać zaproszenia do zespołu (przeciwieństwo dodawania bo tu zespół zaprasza)
   - Dodać wszelkie operacje CURD dla stworzonych obiektów i cały flow (taki podstawowy chociaż)
   - Dodać tworzenie blogów i wgl ??? (może ver 2?)
   - Rozwiązać wszelkie TODO
   - Dodać testy
   - Dodać notyfikacje i subsjrypcje
* */

/**
 * TODO V2
 * Poprawić role na (user_id, role_id, team_id) - jedna osoba może być w kilku zespołach i mieć różne role (nawet kilka w jednym)
 *  Dodać subskrypcje i powiadomienia
 *  Dodać reqrutacje/ nabór do zespołu (zespół zaprasza userów (dowolny czas), userzy wysyłają requesty(gdy rekrutacja otwarta) i zespoły mogą mieć wytyczne
 * Przygotować mockowe endpointy do logowania i rejestracji
 */
