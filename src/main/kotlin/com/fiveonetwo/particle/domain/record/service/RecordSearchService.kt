package com.fiveonetwo.particle.domain.record.service

import com.fiveonetwo.particle.domain.record.entity.Record
import com.fiveonetwo.particle.domain.record.entity.RecordSearch
import com.fiveonetwo.particle.domain.record.repository.RecordSearchRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class RecordSearchService(
    private val recordSearchRepository: RecordSearchRepository,
) {
    @Transactional
    fun create(record: Record) {
        val recordSearch = RecordSearch(
            record = record,
            totalContent = record.title + record.items.joinToString(separator = "") { item -> item.content }
        )
        recordSearchRepository.save(recordSearch)
    }

    fun findAllContentContains(target: String): List<RecordSearch> =
        recordSearchRepository.findAllByTotalContentContains(target)
}