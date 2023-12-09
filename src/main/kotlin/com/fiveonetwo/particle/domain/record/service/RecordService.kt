package com.fiveonetwo.particle.domain.record.service

import com.fiveonetwo.particle.domain.record.dto.RecordCreateDTO
import com.fiveonetwo.particle.domain.record.dto.RecordReadDTO
import com.fiveonetwo.particle.domain.record.entity.Record
import com.fiveonetwo.particle.domain.record.entity.Tag
import com.fiveonetwo.particle.domain.record.error.RecordNotFoundException
import com.fiveonetwo.particle.domain.record.repository.RecordRepository
import com.fiveonetwo.particle.domain.user.entity.User
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class RecordService(
    private val recordRepository: RecordRepository,
    private val recordSearchService: RecordSearchService,
) {
    // command
    @Transactional
    fun createRecord(user: User, create: RecordCreateDTO): RecordReadDTO = recordRepository.save(create.toRecord(user = user))
        .also { record -> recordSearchService.create(record) }
        .let { record -> RecordReadDTO.from(record) }

    @Transactional
    fun deleteRecord(recordId: String) = recordRepository.deleteById(recordId)

    fun findMyRecords(loginUser: User): List<Record> = recordRepository.findAllByUserOrderByCreatedAtDesc(user = loginUser)

    fun mustFindById(recordId: String) = recordRepository.findByIdOrNull(recordId) ?: throw RecordNotFoundException()

    fun findAllByUserAndContainTitle(user: User, title: String): List<Record> =
        recordRepository.findAllByUserAndTitleLikeOrderByCreatedAtDesc(user, "$title%")

    fun findAllByUserAndContainTitleAndContent(user: User, target: String): List<RecordReadDTO> =
        recordSearchService.findAllContentContains(target)
            .map { RecordReadDTO.from(it.record) }

    fun pagedOtherPersonRecordsByTags(pageable: Pageable, tags: List<Tag>, currentUser: User): Page<RecordReadDTO> =
        recordRepository.pagedOtherPersonRecordsByTags(pageable, tags, currentUser)
            .map { RecordReadDTO.from(it) }

    fun save(record: Record): Record = recordRepository.save(record)

    fun deleteAllByUser(user: User) = recordRepository.deleteAllByUser(user)

    fun findRandomRecord(): Record? = recordRepository.findRandomRecord()
}