package com.fiveonetwo.particle.domain.record.repository

import com.fiveonetwo.particle.domain.record.entity.Record
import com.fiveonetwo.particle.domain.record.entity.RecordItem
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

@Repository
interface RecordItemRepository : JpaRepository<RecordItem, String> {
    @Modifying
    @Query("delete RecordItem ri where ri.record = :record")
    fun deleteAllByRecord(record: Record)
}