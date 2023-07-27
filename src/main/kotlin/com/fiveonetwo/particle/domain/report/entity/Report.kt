package com.fiveonetwo.particle.domain.report.entity

import com.fiveonetwo.particle.domain.record.entity.Record
import com.fiveonetwo.particle.domain.user.entity.User
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.Table

@Entity
@Table(name = "reports")
class Report(
    @Id
    @Column(name = "report_id")
    var id: String = "-1",
    @ManyToOne(targetEntity = Record::class, fetch = FetchType.LAZY)
    @JoinColumn(name = "record_id")
    val record: Record,
    @ManyToOne(targetEntity = User::class, fetch = FetchType.LAZY)
    @JoinColumn(name = "reporter_id")
    val reporter: User,
)