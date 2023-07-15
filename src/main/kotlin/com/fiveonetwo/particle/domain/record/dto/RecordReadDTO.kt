package com.fiveonetwo.particle.domain.record.dto

import com.fiveonetwo.particle.domain.record.entity.Record
import com.fiveonetwo.particle.domain.record.entity.RecordItem
import com.fiveonetwo.particle.domain.tag.entity.RecordTag
import com.fiveonetwo.particle.domain.tag.entity.RecordTagValue
import java.time.LocalDateTime

class RecordReadDTO(
    val recordId: String,
    val title: String,
    val url: String,
    val createdAt: LocalDateTime,
    val createdBy: String,
    val items: List<RecordItemReadDTO>,
    val tags: List<RecordTagValue>
) {
    companion object {
        fun from(record: Record, items: List<RecordItem>, tags: List<RecordTag>): RecordReadDTO {
            return RecordReadDTO(
                recordId = record.id,
                title = record.title,
                url = record.url,
                createdAt = record.createdAt,
                createdBy = record.createdBy,
                items = items.map {
                    RecordItemReadDTO(
                        content = it.content,
                        isMain = it.isMain,
                    )
                },
                tags = tags.map { it.value }
            )
        }
    }
}

class RecordItemReadDTO(
    val content: String,
    val isMain: Boolean,
)