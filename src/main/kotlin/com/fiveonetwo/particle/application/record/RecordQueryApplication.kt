package com.fiveonetwo.particle.application.record

import com.fiveonetwo.particle.domain.record.dto.RecordReadDTO
import com.fiveonetwo.particle.domain.record.entity.Tag
import com.fiveonetwo.particle.domain.record.service.RecordService
import com.fiveonetwo.particle.domain.scrap.UrlScraper
import com.fiveonetwo.particle.domain.user.service.UserService
import com.fiveonetwo.particle.web.record.dto.RecordReadResponse
import org.springframework.stereotype.Component

@Component
class RecordQueryApplication(
    private val recordService: RecordService,
    private val userService: UserService,
) {
    fun searchMyRecordsByTitle(loginId: String, title: String): List<RecordReadResponse> =
        recordService.findAllByUserAndContainTitle(
            user = userService.mustFindById(loginId),
            title = title
        )
            .map { record -> RecordReadDTO.from(record) }
            .map { dto -> RecordReadResponse.from(dto) }

    fun searchMyRecordsByTitleAndContent(loginId: String, target: String): List<RecordReadResponse> =
        recordService.findAllByUserAndContainTitleAndContent(userService.mustFindById(loginId), target)
            .map { dto -> RecordReadResponse.from(dto) }

    fun searchMyRecordByTag(loginId: String, searchTag: String): List<RecordReadResponse> =
        recordService.findMyRecords(loginUser = userService.mustFindById(loginId))
            .filter { record -> record.tags.find { tag -> tag.value == Tag.originalValueOf(searchTag) } != null } // 해당 태그가 포함된 정보 필터링
            .map { record -> RecordReadDTO.from(record) }
            .map { dto -> RecordReadResponse.from(dto) }

    fun searchRecordUrlTitle(url: String): String = UrlScraper.readTitle(url = url)

    fun findRecordById(recordId: String): RecordReadResponse =
        recordService.mustFindById(recordId)
            .let { record -> RecordReadDTO.from(record) }
            .let { dto -> RecordReadResponse.from(dto) }

    fun findMyRecords(loginId: String): List<RecordReadResponse> =
        recordService.findMyRecords(loginUser = userService.mustFindById(loginId))
            .map { record -> RecordReadDTO.from(record) }
            .map { dto -> RecordReadResponse.from(dto) }
}