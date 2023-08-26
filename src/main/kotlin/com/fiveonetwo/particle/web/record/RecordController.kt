package com.fiveonetwo.particle.web.record

import com.fiveonetwo.particle.application.record.RecordCommandApplication
import com.fiveonetwo.particle.application.record.RecordQueryApplication
import com.fiveonetwo.particle.domain.record.dto.RecordUpdateDTO
import com.fiveonetwo.particle.domain.record.entity.Tag
import com.fiveonetwo.particle.domain.record.service.RecordService
import com.fiveonetwo.particle.web.record.dto.RecordCreateRequest
import com.fiveonetwo.particle.web.record.dto.RecordReadResponse
import com.fiveonetwo.particle.web.record.dto.RecordUpdateRequest
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.security.Principal

@RestController
@RequestMapping("/api/v1/record")
class RecordController(
    private val recordService: RecordService,
    private val recordQueryApplication: RecordQueryApplication,
    private val recordCommandApplication: RecordCommandApplication,
) {
    @GetMapping("/{recordId}")
    fun findRecordById(
        @PathVariable
        recordId: String
    ): RecordReadResponse = recordQueryApplication.findRecordById(recordId = recordId)

    @GetMapping("/my")
    fun findMyRecords(
        principal: Principal
    ): List<RecordReadResponse> = recordQueryApplication.findMyRecords(loginId = principal.name)

    @PutMapping("/{recordId}")
    fun updateRecord(
        principal: Principal,
        @RequestBody
        update: RecordUpdateRequest,
        @PathVariable
        recordId: String,
    ): RecordReadResponse = recordCommandApplication.updateMyRecord(
        loginId = principal.name,
        recordId = recordId,
        update = update
    )

    @DeleteMapping("/{recordId}")
    fun deleteRecordById(
        principal: Principal,
        @PathVariable
        recordId: String,
    ): String = recordCommandApplication.deleteMyRecord(loginId = principal.name, recordId = recordId)

    @PostMapping("")
    fun createRecord(
        principal: Principal,
        @RequestBody
        request: RecordCreateRequest
    ): RecordReadResponse = recordCommandApplication.createRecord(loginId = principal.name, request = request)

    @GetMapping("/search/by/title")
    fun searchMyRecordByTitle(
        principal: Principal,
        @RequestParam
        title: String,
    ): List<RecordReadResponse> = recordQueryApplication.searchMyRecordsByTitle(loginId = principal.name, title = title)

    @GetMapping("/search/by/tag")
    fun searchMyRecordByTag(
        principal: Principal,
        @RequestParam
        tag: Tag,
    ): List<RecordReadResponse> = recordQueryApplication.searchMyRecordByTag(loginId = principal.name, searchTag = tag)

    @GetMapping("/search/url/title")
    fun searchRecordUrlTitle(
        @RequestParam
        url: String,
    ): String = recordQueryApplication.searchRecordUrlTitle(url = url)
}