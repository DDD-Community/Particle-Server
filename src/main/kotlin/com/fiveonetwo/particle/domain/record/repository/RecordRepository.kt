package com.fiveonetwo.particle.domain.record.repository

import com.fiveonetwo.particle.domain.record.entity.Record
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface RecordRepository : JpaRepository<Record, String>