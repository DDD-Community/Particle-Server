package com.fiveonetwo.particle.web.record

import com.fiveonetwo.particle.application.record.RecordCommandApplication
import com.fiveonetwo.particle.application.record.RecordQueryApplication
import com.fiveonetwo.particle.domain.record.dto.RecordCreateDTO
import com.fiveonetwo.particle.domain.record.dto.RecordReadDTO
import com.fiveonetwo.particle.domain.record.dto.RecordUpdateDTO
import com.fiveonetwo.particle.domain.record.entity.RecordTagValue
import com.fiveonetwo.particle.domain.record.service.RecordService
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
    ): RecordReadDTO = recordQueryApplication.findRecordById(recordId = recordId)

    @GetMapping("/my")
    fun findMyRecords(
        principal: Principal
    ): List<RecordReadDTO> = recordQueryApplication.findMyRecords(loginId = principal.name)

    @PutMapping("/{recordId}")
    fun updateRecord(
        principal: Principal,
        @RequestBody
        update: RecordUpdateDTO,
        @PathVariable
        recordId: String,
    ): RecordReadDTO = recordCommandApplication.updateMyRecord(loginId = principal.name, recordId = recordId, update = update)

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
        create: RecordCreateDTO
    ): RecordReadDTO = recordService.createRecord(loginId = principal.name, create = create)

    @GetMapping("/search/by/title")
    fun searchMyRecordByTitle(
        principal: Principal,
        @RequestParam
        title: String,
    ): List<RecordReadDTO> = recordQueryApplication.searchMyRecordsByTitle(loginId = principal.name, title = title)

    @GetMapping("/search/by/tag")
    fun searchMyRecordByTag(
        principal: Principal,
        @RequestParam
        tagValue: RecordTagValue,
    ): List<RecordReadDTO> = recordQueryApplication.searchMyRecordByTag(loginId = principal.name, tagValue = tagValue)

    @GetMapping("/search/url/title")
    fun searchRecordUrlTitle(
        @RequestParam
        url: String,
    ): String = recordQueryApplication.searchRecordUrlTitle(url = url)
}