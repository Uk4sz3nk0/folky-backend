package com.lukaszwodniak.folky.config

import com.google.auth.oauth2.GoogleCredentials
import com.google.firebase.FirebaseApp
import com.google.firebase.FirebaseOptions
import com.google.firebase.messaging.FirebaseMessaging
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.io.Resource


/**
 * FirebaseConfiguration
 *
 * Created on: 2024-10-20
 * @author Łukasz Wodniak
 */

@Configuration
class FirebaseConfiguration {

    @Value("\${gcp.firebase.serviceAccount}")
    private lateinit var firebaseConfig: Resource

    @Bean
    fun firebaseApp(): FirebaseApp? {
        try {
            val options = FirebaseOptions.Builder()
                .setCredentials(GoogleCredentials.fromStream(firebaseConfig.inputStream))
                .build()

            return FirebaseApp.initializeApp(options)
        } catch (e: Exception) {
            logger.error("FirebaseApp initialization failed. Reason: ${e.message}", e)
            throw IllegalStateException("Unable to initialize FirebaseApp", e)
        }
    }

    @Bean
    fun firebaseMessaging(firebaseApp: FirebaseApp?): FirebaseMessaging {
        if (firebaseApp == null) {
            throw IllegalStateException("FirebaseApp is null. Cannot initialize FirebaseMessaging.")
        }
        return FirebaseMessaging.getInstance(firebaseApp)
    }

    companion object {
        private val logger = LoggerFactory.getLogger(FirebaseConfiguration::class.java)
    }

}