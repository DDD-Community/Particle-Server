package com.fiveonetwo.particle.domain.alarm.service

import com.fiveonetwo.particle.domain.alarm.entity.Alarm
import com.fiveonetwo.particle.domain.alarm.error.AlarmNotFoundException
import com.fiveonetwo.particle.domain.alarm.repository.AlarmRepository
import com.fiveonetwo.particle.domain.user.entity.User
import com.fiveonetwo.particle.shared.utils.logger
import com.google.firebase.messaging.AndroidConfig
import com.google.firebase.messaging.FirebaseMessaging
import com.google.firebase.messaging.Message
import com.google.firebase.messaging.Notification
import org.slf4j.Logger
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class AlarmService(
    private val alarmRepository: AlarmRepository,
    private val firebaseMessaging: FirebaseMessaging,
) {
    private val log: Logger = logger<AlarmService>()

    @Transactional
    fun create(entity: Alarm): Alarm = alarmRepository.save(entity)

    @Transactional
    fun save(entity: Alarm): Alarm = alarmRepository.save(entity)

    @Transactional
    fun delete(entity: Alarm) = alarmRepository.delete(entity)

    fun findMyAlarms(user: User): List<Alarm> {
        return alarmRepository.findAllByUser(user)
    }

    fun send(title: String, body: String, topic: String) {
        log.info("send message : {title : $title, body : $body, topic : $topic}")
        val androidConfig = createDefaultAndroidConfig()
        val notification = createNotification(title, body)
        val message = Message.builder()
            .setNotification(notification)
            .setAndroidConfig(androidConfig)
            .setTopic(topic)
            .build()

        firebaseMessaging.send(message)
    }

    fun sendUsingToken(title: String, body: String, token: String, data: MutableMap<String, String>) {
        log.info("send message : {title : $title, body : $body, token : $token}")
        val androidConfig = createDefaultAndroidConfig()
        val notification = createNotification(title, body)
        val message = Message.builder()
            .setNotification(notification)
            .setAndroidConfig(androidConfig)
            .setToken(token)
            .putAllData(data)
            .build()

        firebaseMessaging.send(message)
    }

    fun createDefaultAndroidConfig(): AndroidConfig = AndroidConfig.builder()
        .setPriority(AndroidConfig.Priority.HIGH)
        .build()

    fun createNotification(title: String, body: String): Notification = Notification.builder()
        .setTitle(title)
        .setBody(body)
        .build()

    fun mustFindById(id: String): Alarm = alarmRepository.findByIdOrNull(id) ?: throw AlarmNotFoundException()
}