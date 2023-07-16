package com.fiveonetwo.particle.infra.record.repository

import com.fiveonetwo.particle.domain.record.entity.RecordItem
import org.springframework.data.r2dbc.repository.R2dbcRepository
import reactor.core.publisher.Flux

interface RecordItemRepository : R2dbcRepository<RecordItem, String> {
    suspend fun getAllByRecordId(recordId: String): Flux<RecordItem>
}