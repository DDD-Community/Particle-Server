package com.fiveonetwo.particle.config

import com.google.auth.oauth2.GoogleCredentials
import com.google.firebase.FirebaseApp.initializeApp
import com.google.firebase.FirebaseOptions
import com.google.firebase.messaging.FirebaseMessaging
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import java.io.FileInputStream


@Configuration
class FirebaseConfig(
    @Value(value = "\${firebase.secret-file-path}")
    private val secretFilePath: String
) {
    @Bean
    fun firebaseMessaging(): FirebaseMessaging? {
        val serviceAccount = FileInputStream(secretFilePath)
        val options = FirebaseOptions.builder()
            .setCredentials(GoogleCredentials.fromStream(serviceAccount))
            .build()
        val app = initializeApp(options)
        return FirebaseMessaging.getInstance(app)
    }
}