package com.fiveonetwo.particle.domain.record.repository

import com.fiveonetwo.particle.domain.record.entity.Record
import com.fiveonetwo.particle.domain.record.entity.RecordTag
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

@Repository
interface RecordTagRepository : JpaRepository<RecordTag, String> {
    @Modifying
    @Query("delete RecordTag rt where rt.record = :record")
    fun deleteAllByRecord(record: Record)
}