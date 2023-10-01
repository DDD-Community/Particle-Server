package com.fiveonetwo.particle.domain.record.repository

import com.fiveonetwo.particle.domain.record.entity.Record
import com.fiveonetwo.particle.domain.user.entity.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

@Repository
interface RecordRepository : JpaRepository<Record, String> {
    fun findAllByUserOrderByCreatedAtDesc(user: User): List<Record>
    fun findAllByUserAndTitleLikeOrderByCreatedAtDesc(user: User, title: String): List<Record>
    fun deleteAllByUser(user: User)

    @Query(value = "select * from records order by RAND() limit 1", nativeQuery = true)
    fun findRandomRecord(): Record?
}