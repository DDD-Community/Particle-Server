package com.fiveonetwo.particle.application.record

import com.fiveonetwo.particle.domain.record.dto.RecordReadDTO
import com.fiveonetwo.particle.domain.record.entity.RecordTagValue
import com.fiveonetwo.particle.domain.record.service.RecordService
import com.fiveonetwo.particle.domain.scrap.UrlScraper
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

    fun searchMyRecordByTag(loginId: String, tagValue: RecordTagValue): List<RecordReadDTO> {
        val loginUser = userService.mustFindById(loginId)

        return recordService.findMyRecords(loginUser)
            .filter { record -> record.tags.find { tag -> tag.value == tagValue } != null } // 해당 태그가 포함된 정보 필터링
            .map { record -> RecordReadDTO.from(record) }
    }

    fun searchRecordUrlTitle(url: String): String = UrlScraper.readTitle(url = url)
}