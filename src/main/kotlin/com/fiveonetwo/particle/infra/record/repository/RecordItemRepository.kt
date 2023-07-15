package com.fiveonetwo.particle.infra.record.repository

import com.fiveonetwo.particle.domain.record.entity.RecordItem
import org.springframework.data.r2dbc.repository.R2dbcRepository

interface RecordItemRepository  : R2dbcRepository<RecordItem, String>{
    suspend fun getAllByRecordId(recordId: String): List<RecordItem>
}