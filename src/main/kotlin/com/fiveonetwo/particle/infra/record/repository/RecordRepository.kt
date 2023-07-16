package com.fiveonetwo.particle.infra.record.repository

import com.fiveonetwo.particle.domain.record.entity.Record
import org.springframework.data.r2dbc.repository.R2dbcRepository
import org.springframework.stereotype.Repository
import reactor.core.publisher.Flux

@Repository
interface RecordRepository : R2dbcRepository<Record, String>{
    fun getAllByUserId(userId: String): Flux<Record>
}