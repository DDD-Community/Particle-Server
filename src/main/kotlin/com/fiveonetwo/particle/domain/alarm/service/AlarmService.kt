package com.fiveonetwo.particle.domain.alarm.service

import com.fiveonetwo.particle.domain.alarm.entity.Alarm
import com.fiveonetwo.particle.domain.alarm.repository.AlarmRepository
import com.fiveonetwo.particle.domain.user.entity.User
import com.fiveonetwo.particle.shared.utils.logger
import com.google.firebase.messaging.AndroidConfig
import com.google.firebase.messaging.FirebaseMessaging
import com.google.firebase.messaging.Message
import com.google.firebase.messaging.Notification
import org.slf4j.Logger
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
    fun create(entity: Alarm) {
        alarmRepository.save(entity)
    }

    @Transactional
    fun save(entity: Alarm): Alarm {
        return alarmRepository.save(entity)
    }

    @Transactional
    fun delete(entity: Alarm) {
        alarmRepository.delete(entity)
    }

    fun findMyAlarms(user: User): List<Alarm> {
        return alarmRepository.findAllByUser(user)
    }

    fun send(title: String, body: String, topic: String) {
        log.info("send message : {title : $title, body : $body, topic : $topic}")
        val androidConfig = AndroidConfig.builder()
            .setPriority(AndroidConfig.Priority.HIGH)
            .build()

        val notification = Notification.builder()
            .setTitle(title)
            .setBody(body)
            .build()

        val message = Message.builder()
            .setNotification(notification)
            .setAndroidConfig(androidConfig)
            .setTopic(topic)
            .build()

        firebaseMessaging.send(message)
    }
}