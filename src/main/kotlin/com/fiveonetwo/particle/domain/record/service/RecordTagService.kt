package com.fiveonetwo.particle.domain.record.service

import com.fiveonetwo.particle.domain.record.entity.Record
import com.fiveonetwo.particle.domain.record.repository.RecordTagRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class RecordTagService(
    private val recordTagRepository: RecordTagRepository,
) {
    @Transactional
    fun deleteAllByRecord(record: Record) = recordTagRepository.deleteAllByRecord(record)
}