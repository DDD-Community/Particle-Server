package com.fiveonetwo.particle.domain.record.service

import com.fiveonetwo.particle.domain.record.entity.Record
import com.fiveonetwo.particle.domain.record.repository.RecordItemRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class RecordItemService(
    private val recordItemRepository: RecordItemRepository,
) {
    @Transactional
    fun deleteAllByRecord(record: Record) = recordItemRepository.deleteAllByRecord(record)
}