package com.fiveonetwo.particle.application.record

import com.fiveonetwo.particle.domain.record.dto.RecordCreateDTO
import com.fiveonetwo.particle.domain.record.dto.RecordReadDTO
import com.fiveonetwo.particle.domain.record.dto.RecordUpdateDTO
import com.fiveonetwo.particle.domain.record.entity.Record
import com.fiveonetwo.particle.domain.record.service.RecordService
import com.fiveonetwo.particle.domain.user.entity.User
import com.fiveonetwo.particle.domain.user.service.UserService
import com.fiveonetwo.particle.shared.security.exception.AuthorizationException
import com.fiveonetwo.particle.web.record.dto.RecordCreateRequest
import com.fiveonetwo.particle.web.record.dto.RecordReadResponse
import com.fiveonetwo.particle.web.record.dto.RecordUpdateRequest
import org.springframework.stereotype.Component


@Component
class RecordCommandApplication(
    private val recordService: RecordService,
    private val userService: UserService,
) {
    fun createRecord(loginId: String, request: RecordCreateRequest): RecordReadResponse =
        recordService.createRecord(
            user = userService.mustFindById(loginId),
            create = RecordCreateDTO.from(request)
        ).let { dto -> RecordReadResponse.from(dto) }

    fun updateMyRecord(loginId: String, recordId: String, update: RecordUpdateRequest): RecordReadResponse {
        val loginUser = userService.mustFindById(userId = loginId)
        val record = recordService.mustFindById(recordId)
        val dto = RecordUpdateDTO.from(update)

        validate(user = loginUser, record = record)

        recordService.deleteRecord(recordId)
        return recordService.save(dto.toRecord(loginUser))
            .let { entity -> RecordReadDTO.from(entity) }
            .let { RecordReadResponse.from(dto = it) }
    }

    fun deleteMyRecord(loginId: String, recordId: String): String {
        val loginUser = userService.mustFindById(userId = loginId)
        val record = recordService.mustFindById(recordId)

        validate(user = loginUser, record = record)
        recordService.deleteRecord(recordId)
        return recordId
    }

    fun validate(user: User, record: Record) {
        if (user.id != record.user.id)
            throw AuthorizationException()
    }
}