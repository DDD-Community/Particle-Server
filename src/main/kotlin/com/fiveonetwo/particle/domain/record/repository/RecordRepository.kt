package com.fiveonetwo.particle.domain.record.repository

import com.fiveonetwo.particle.domain.record.entity.Record
import com.fiveonetwo.particle.domain.user.entity.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface RecordRepository : JpaRepository<Record, String> {
    fun findAllByUser(user: User): List<Record>
    fun findAllByUserAndTitleLikeOrderByCreatedAtDesc(user: User, title: String): List<Record>
}