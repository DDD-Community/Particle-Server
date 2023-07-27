package com.fiveonetwo.particle.domain.report.service

import com.fiveonetwo.particle.domain.record.service.RecordService
import com.fiveonetwo.particle.domain.report.entity.Report
import com.fiveonetwo.particle.domain.report.repository.ReportRepository
import com.fiveonetwo.particle.domain.user.service.UserService
import com.fiveonetwo.particle.shared.utils.uuid
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class ReportService(
    private val reportRepository: ReportRepository,
    private val recordService: RecordService,
    private val userService: UserService,
) {
    @Transactional
    fun report(loginId: String, recordId: String) {
        val record = recordService.mustFindById(recordId)
        val user = userService.mustFindById(loginId)

        reportRepository.save(Report(id = uuid(), record = record, reporter = user))
    }
}