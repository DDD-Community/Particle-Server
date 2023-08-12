package com.fiveonetwo.particle.web.record

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
) {
    @GetMapping("/{recordId}")
    fun findRecordById(@PathVariable recordId: String) = recordService.findRecordById(recordId = recordId)

    @GetMapping("/my")
    fun findMyRecords(principal: Principal) = recordService.findMyRecords(loginId = principal.name)

    @PutMapping("/{recordId}")
    fun updateRecord(
        principal: Principal,
        @RequestBody
        update: RecordUpdateDTO,
        @PathVariable
        recordId: String,
    ): RecordReadDTO = recordService.updateRecord(principal.name, recordId, update)

    @DeleteMapping("/{recordId}")
    fun deleteRecordById(
        principal: Principal,
        @PathVariable
        recordId: String,
    ): String = recordService.deleteRecord(loginId = principal.name, recordId = recordId)

    @PostMapping("")
    fun createRecord(principal: Principal, @RequestBody create: RecordCreateDTO) = recordService.createRecord(principal.name, create)

    @GetMapping("/search/by/title")
    fun searchMyRecordByTitle(
        principal: Principal,
        @RequestParam title: String,
    ): List<RecordReadDTO> = recordQueryApplication.searchMyRecordsByTitle(principal.name, title)

    @GetMapping("/search/by/tag")
    fun searchMyRecordByTag(
        principal: Principal,
        @RequestParam tagValue: RecordTagValue,
    ): List<RecordReadDTO> = recordQueryApplication.searchMyRecordByTag(principal.name, tagValue)
}