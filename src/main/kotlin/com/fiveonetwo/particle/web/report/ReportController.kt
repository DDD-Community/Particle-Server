package com.fiveonetwo.particle.web.report

import com.fiveonetwo.particle.domain.report.service.ReportService
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.security.Principal


@RestController
@RequestMapping("/api/v1/report")
class ReportController(
    private val reportService: ReportService,
) {
    @PostMapping("/{reportId}")
    fun report(principal: Principal, @PathVariable(name = "reportId") reportId: String) {
        reportService.report(principal.name, reportId)
    }
}