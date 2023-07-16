package com.fiveonetwo.particle.web.record

import com.fiveonetwo.particle.domain.record.dto.RecordCreateDTO
import com.fiveonetwo.particle.domain.record.service.RecordService
import org.springframework.web.bind.annotation.*
import java.security.Principal

@RestController
@RequestMapping("/api/v1/record")
class RecordController(
        private val recordService: RecordService
) {
    @PostMapping("")
    fun createRecord(principal: Principal,@RequestBody create: RecordCreateDTO) = recordService.createRecord(principal.name, create)

    @GetMapping("/{recordId}")
    fun findRecordById(@PathVariable recordId: String) = recordService.findRecordById(recordId = recordId)

    @GetMapping("/my")
    fun findMyRecords(principal: Principal) = recordService.findMyRecords(loginId = principal.name)

    @DeleteMapping("/{recordId}")
    fun deleteRecordById(principal: Principal, @PathVariable recordId: String) : String = recordService.deleteRecord(loginId = principal.name, recordId = recordId)
}