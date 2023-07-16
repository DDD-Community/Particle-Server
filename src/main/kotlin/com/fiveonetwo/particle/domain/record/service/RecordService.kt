package com.fiveonetwo.particle.domain.record.service

import com.fiveonetwo.particle.domain.record.dto.RecordCreateDTO
import com.fiveonetwo.particle.domain.record.dto.RecordReadDTO
import com.fiveonetwo.particle.domain.record.error.RecordNotFoundException
import com.fiveonetwo.particle.domain.record.repository.RecordRepository
import com.fiveonetwo.particle.domain.user.error.UserNotFoundException
import com.fiveonetwo.particle.domain.user.repository.UserRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class RecordService(
        private val recordRepository: RecordRepository,
        private val userRepository: UserRepository,
) {
    fun createRecord(loginId: String, create: RecordCreateDTO): RecordReadDTO {
        val user = userRepository.findByIdOrNull(loginId) ?: throw UserNotFoundException()
        return recordRepository.save(create.toRecord(user = user))
                .let { record -> RecordReadDTO.from(record) }
    }

    fun deleteRecord(loginId: String, recordId: String): String {
        val user = userRepository.findByIdOrNull(loginId) ?: throw UserNotFoundException()
        val record = recordRepository.findByIdOrNull(recordId) ?: throw RecordNotFoundException()
        if (record.user.id != user.id) throw IllegalAccessException()
        recordRepository.deleteById(recordId)
        return record.id
    }

    @Transactional(readOnly = true)
    fun findRecordById(recordId: String): RecordReadDTO =
            (recordRepository.findByIdOrNull(recordId) ?: throw RecordNotFoundException())
                    .let { record -> RecordReadDTO.from(record) }

    @Transactional(readOnly = true)
    fun findMyRecords(loginId: String): List<RecordReadDTO> {
        val user = userRepository.findByIdOrNull(loginId) ?: throw UserNotFoundException()
        return recordRepository.findAllByUser(user).map { record -> RecordReadDTO.from(record) }
    }
}