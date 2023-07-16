package com.fiveonetwo.particle.infra.tag

import com.fiveonetwo.particle.domain.tag.entity.RecordTag
import org.springframework.data.r2dbc.repository.R2dbcRepository
import org.springframework.stereotype.Repository
import reactor.core.publisher.Flux

@Repository
interface RecordTagRepository : R2dbcRepository<RecordTag, String> {
    fun getAllByRecordId(recordId: String): Flux<RecordTag>
}