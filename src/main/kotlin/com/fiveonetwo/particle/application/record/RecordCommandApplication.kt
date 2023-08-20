package com.fiveonetwo.particle.application.record

import com.fiveonetwo.particle.domain.record.dto.RecordReadDTO
import com.fiveonetwo.particle.domain.record.dto.RecordUpdateDTO
import com.fiveonetwo.particle.domain.record.entity.Record
import com.fiveonetwo.particle.domain.record.service.RecordService
import com.fiveonetwo.particle.domain.user.entity.User
import com.fiveonetwo.particle.domain.user.service.UserService
import com.fiveonetwo.particle.shared.security.exception.AuthorizationException
import org.springframework.stereotype.Component


@Component
class RecordCommandApplication(
    private val recordService: RecordService,
    private val userService: UserService,
) {
    fun updateMyRecord(loginId: String, recordId: String, update: RecordUpdateDTO): RecordReadDTO {
        val loginUser = userService.mustFindById(userId = loginId)
        val record = recordService.mustFindById(recordId)

        if (validate(user = loginUser, record = record))
            throw AuthorizationException()

        recordService.deleteRecord(recordId)
        return recordService.save(update.toRecord(loginUser)).let { RecordReadDTO.from(it) }
    }

    fun deleteMyRecord(loginId: String, recordId: String): String {
        val loginUser = userService.mustFindById(userId = loginId)
        val record = recordService.mustFindById(recordId)

        if (validate(user = loginUser, record = record))
            throw AuthorizationException()

        recordService.deleteRecord(recordId)
        return recordId
    }

    fun validate(user: User, record: Record): Boolean = (user.id == record.user.id)
}