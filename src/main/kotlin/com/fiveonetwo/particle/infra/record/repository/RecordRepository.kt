package com.fiveonetwo.particle.infra.record.repository

import com.fiveonetwo.particle.domain.record.entity.Record
import org.springframework.data.r2dbc.repository.R2dbcRepository
import org.springframework.stereotype.Repository

@Repository
interface RecordRepository : R2dbcRepository<Record, String>