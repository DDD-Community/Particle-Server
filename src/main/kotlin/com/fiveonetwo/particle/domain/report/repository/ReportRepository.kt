package com.fiveonetwo.particle.domain.report.repository

import com.fiveonetwo.particle.domain.report.entity.Report
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface ReportRepository : JpaRepository<Report, String>