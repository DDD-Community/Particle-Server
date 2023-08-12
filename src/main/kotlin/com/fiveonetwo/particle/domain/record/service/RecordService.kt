package com.fiveonetwo.particle.domain.record.service

import com.fiveonetwo.particle.domain.record.dto.RecordCreateDTO
import com.fiveonetwo.particle.domain.record.dto.RecordReadDTO
import com.fiveonetwo.particle.domain.record.dto.RecordUpdateDTO
import com.fiveonetwo.particle.domain.record.entity.Record
import com.fiveonetwo.particle.domain.record.error.RecordNotFoundException
import com.fiveonetwo.particle.domain.record.repository.RecordRepository
import com.fiveonetwo.particle.domain.user.entity.User
import com.fiveonetwo.particle.domain.user.service.UserService
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class RecordService(
    private val recordRepository: RecordRepository,
    private val userService: UserService,
) {
    // command
    @Transactional
    fun createRecord(loginId: String, create: RecordCreateDTO): RecordReadDTO {
        val user = userService.mustFindById(loginId)
        return recordRepository.save(create.toRecord(user = user)).let { record -> RecordReadDTO.from(record) }
    }

    @Transactional
    fun deleteRecord(loginId: String, recordId: String): String {
        val user = userService.mustFindById(loginId)
        val record = mustFindById(recordId)
        if (record.user.id != user.id) throw IllegalAccessException()
        recordRepository.deleteById(recordId)
        return record.id
    }

    @Transactional
    fun updateRecord(loginId: String, recordId: String, update: RecordUpdateDTO): RecordReadDTO {
        val user = userService.mustFindById(loginId)
        recordRepository.deleteById(recordId)
        return recordRepository.save(update.toRecord(user)).let { RecordReadDTO.from(it) }
    }

    // query
    fun findRecordById(recordId: String): RecordReadDTO = mustFindById(recordId).let { record -> RecordReadDTO.from(record) }

    fun findMyRecords(loginId: String): List<RecordReadDTO> {
        val user = userService.mustFindById(loginId)
        return recordRepository.findAllByUser(user).map { record -> RecordReadDTO.from(record) }
    }

    fun mustFindById(recordId: String) = recordRepository.findByIdOrNull(recordId) ?: throw RecordNotFoundException()

    fun findAllByUserAndContainTitle(user: User, title: String): List<Record> =
        recordRepository.findAllByUserAndTitleLikeOrderByCreatedAtDesc(user, "$title%")
}