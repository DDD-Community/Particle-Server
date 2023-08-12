package com.fiveonetwo.particle.application.record

import com.fiveonetwo.particle.domain.record.dto.RecordReadDTO
import com.fiveonetwo.particle.domain.record.service.RecordService
import com.fiveonetwo.particle.domain.user.service.UserService
import org.springframework.stereotype.Component

@Component
class RecordQueryApplication(
    private val recordService: RecordService,
    private val userService: UserService,
) {
    fun searchMyRecordsByTitle(loginId: String, title: String): List<RecordReadDTO> {
        val loginUser = userService.mustFindById(loginId)
        return recordService.findAllByUserAndContainTitle(loginUser, title).map { record -> RecordReadDTO.from(record) }
    }
}