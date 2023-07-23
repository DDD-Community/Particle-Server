package com.fiveonetwo.particle.web.record

import com.fiveonetwo.particle.domain.record.dto.RecordCreateDTO
import com.fiveonetwo.particle.domain.record.dto.RecordReadDTO
import com.fiveonetwo.particle.domain.record.dto.RecordUpdateDTO
import com.fiveonetwo.particle.domain.record.service.RecordService
import org.springframework.web.bind.annotation.*
import java.security.Principal

@RestController
@RequestMapping("/api/v1/record")
class RecordController(
    private val recordService: RecordService
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
}