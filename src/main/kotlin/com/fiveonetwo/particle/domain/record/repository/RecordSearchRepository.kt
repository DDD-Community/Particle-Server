package com.fiveonetwo.particle.domain.record.repository

import com.fiveonetwo.particle.domain.record.entity.Record
import com.fiveonetwo.particle.domain.record.entity.RecordSearch
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

@Repository
interface RecordSearchRepository : JpaRepository<RecordSearch, String> {
    fun findAllByTotalContentContains(target: String): List<RecordSearch>
    @Modifying
    @Query("delete RecordSearch rs where rs.record = :record")
    fun deleteByRecord(record: Record)
}