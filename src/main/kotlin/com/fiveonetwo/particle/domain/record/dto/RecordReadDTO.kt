package com.fiveonetwo.particle.domain.record.dto

import com.fiveonetwo.particle.domain.record.entity.Record
import com.fiveonetwo.particle.domain.record.entity.RecordColor
import com.fiveonetwo.particle.domain.record.entity.RecordStyle
import com.fiveonetwo.particle.domain.record.entity.Tag
import java.time.LocalDateTime

class RecordReadDTO(
    val id: String,
    val title: String,
    val url: String,
    val items: List<RecordItemReadDTO>,
    val tags: List<Tag>,
    val attribute: RecordReadAttributeDTO,
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
            attribute = RecordReadAttributeDTO(
                color = record.attribute.color,
                style = record.attribute.style,
            ),
            createdBy = record.user.nickname,
            createdAt = record.createdAt
        )
    }
}

class RecordItemReadDTO(
    val content: String,
    val isMain: Boolean
)

class RecordReadAttributeDTO(
    val color: RecordColor,
    val style: RecordStyle,
)