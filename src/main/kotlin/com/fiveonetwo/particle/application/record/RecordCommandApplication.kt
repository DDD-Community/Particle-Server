package com.fiveonetwo.particle.application.record

import com.fiveonetwo.particle.domain.record.dto.RecordCreateDTO
import com.fiveonetwo.particle.domain.record.dto.RecordReadDTO
import com.fiveonetwo.particle.domain.record.entity.Record
import com.fiveonetwo.particle.domain.record.entity.RecordItem
import com.fiveonetwo.particle.domain.record.entity.RecordTag
import com.fiveonetwo.particle.domain.record.entity.Tag
import com.fiveonetwo.particle.domain.record.service.RecordItemService
import com.fiveonetwo.particle.domain.record.service.RecordSearchService
import com.fiveonetwo.particle.domain.record.service.RecordService
import com.fiveonetwo.particle.domain.record.service.RecordTagService
import com.fiveonetwo.particle.domain.user.entity.User
import com.fiveonetwo.particle.domain.user.service.UserService
import com.fiveonetwo.particle.shared.security.exception.AuthorizationException
import com.fiveonetwo.particle.web.record.dto.RecordCreateRequest
import com.fiveonetwo.particle.web.record.dto.RecordReadResponse
import com.fiveonetwo.particle.web.record.dto.RecordUpdateRequest
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional


@Component
class RecordCommandApplication(
    private val recordService: RecordService,
    private val recordSearchService: RecordSearchService,
    private val recordTagService: RecordTagService,
    private val recordItemService: RecordItemService,
    private val userService: UserService,
) {
    fun createRecord(loginId: String, request: RecordCreateRequest): RecordReadResponse =
        recordService.createRecord(
            user = userService.mustFindById(loginId),
            create = RecordCreateDTO.from(request)
        ).let { dto -> RecordReadResponse.from(dto) }

    @Transactional
    fun updateMyRecord(loginId: String, recordId: String, update: RecordUpdateRequest): RecordReadResponse {
        val loginUser = userService.mustFindById(userId = loginId)
        val record = recordService.mustFindById(recordId)

        validate(user = loginUser, record = record)

        recordTagService.deleteAllByRecord(record)
        recordItemService.deleteAllByRecord(record)

        record.update(
            title = update.title,
            url = update.url,
            tags = update.tags.map { tagValue -> RecordTag(record = record, value = Tag.originalValueOf(tagValue)) }.toMutableList(),
            items = update.items.map { item -> RecordItem(record = record, content = item.content, isMain = item.isMain) }.toMutableList(),
            style = update.style
        )

        recordSearchService.update(record)

        return record
            .let { entity -> RecordReadDTO.from(entity) }
            .let { RecordReadResponse.from(dto = it) }
    }

    @Transactional
    fun deleteMyRecord(loginId: String, recordId: String): String {
        val loginUser = userService.mustFindById(userId = loginId)
        val record = recordService.mustFindById(recordId)

        validate(user = loginUser, record = record)
        recordSearchService.deleteByRecord(record)
        recordService.deleteRecord(recordId)
        return recordId
    }

    fun validate(user: User, record: Record) {
        if (user.id != record.user.id)
            throw AuthorizationException()
    }
}