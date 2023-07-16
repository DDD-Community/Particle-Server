package com.fiveonetwo.particle.domain.record.dto

import com.fiveonetwo.particle.domain.record.entity.Record
import com.fiveonetwo.particle.domain.record.entity.RecordTagValue
import com.fiveonetwo.particle.domain.user.entity.User
import java.time.LocalDateTime

class RecordReadDTO(
        val id: String,
        val title: String,
        val url: String,
        val items: List<RecordItemReadDTO>,
        val tags: List<RecordTagValue>,
        val createdAt: LocalDateTime,
        val createdBy: String,
) {
    companion object {
        fun from(record: Record) = RecordReadDTO(
                id = record.id,
                title = record.title,
                url = record.url,
                items = record.items.map { RecordItemReadDTO(it.content, it.isMain) },
                tags = record.tags.map { it.value },
                createdBy = record.user.nickname,
                createdAt = record.createdAt
        )
    }
}

class RecordItemReadDTO(
        val content: String,
        val isMain: Boolean
)