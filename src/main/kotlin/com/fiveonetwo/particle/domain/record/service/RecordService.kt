package com.fiveonetwo.particle.domain.record.service

import com.fiveonetwo.particle.domain.record.dto.RecordCreateDTO
import com.fiveonetwo.particle.domain.record.dto.RecordReadDTO
import com.fiveonetwo.particle.infra.record.repository.RecordItemRepository
import com.fiveonetwo.particle.infra.record.repository.RecordRepository
import com.fiveonetwo.particle.infra.tag.RecordTagRepository
import com.fiveonetwo.particle.infra.user.repository.UserRepository
import kotlinx.coroutines.reactor.awaitSingle
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class RecordService(
    private val recordRepository: RecordRepository,
    private val recordItemRepository: RecordItemRepository,
    private val recordTagRepository: RecordTagRepository,
    private val userRepository: UserRepository,
) {
    @Transactional
    suspend fun createRecord(loginId: String, create: RecordCreateDTO): RecordReadDTO {
        val user = userRepository.findById(loginId).awaitSingle()
        val record = recordRepository.save(create.toRecord(user)).awaitSingle()
        val items = create.toRecordItems(record.id)
        val tags = create.toRecordTags(record.id)
        recordItemRepository.saveAll(items).collectList().awaitSingle()
        recordTagRepository.saveAll(tags).collectList().awaitSingle()
        return RecordReadDTO.from(record, items, tags)
    }
}