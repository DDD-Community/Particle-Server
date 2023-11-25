package com.fiveonetwo.particle.web.record.dto

import com.fiveonetwo.particle.domain.record.dto.RecordReadDTO
import com.fiveonetwo.particle.domain.record.entity.RecordColor
import com.fiveonetwo.particle.domain.record.entity.RecordStyle
import java.time.LocalDateTime

class RecordReadResponse(
    val id: String,
    val title: String,
    val url: String,
    val items: List<RecordItemReadResponse>,
    val tags: List<String>,
    val attribute: RecordReadAttributeResponse,
    val createdAt: LocalDateTime,
    val createdBy: String,
) {
    companion object {
        fun from(dto: RecordReadDTO): RecordReadResponse {
            return RecordReadResponse(
                id = dto.id,
                title = dto.title,
                url = dto.url,
                items = dto.items.map { item ->
                    RecordItemReadResponse(
                        content = item.content,
                        isMain = item.isMain
                    )
                },
                tags = dto.tags.map { tag -> tag.value },
                attribute = RecordReadAttributeResponse(
                    color = dto.attribute.color,
                    style = dto.attribute.style,
                ),
                createdAt = dto.createdAt,
                createdBy = dto.createdBy
            )
        }
    }
}

class RecordItemReadResponse(
    val content: String,
    val isMain: Boolean,
)

class RecordReadAttributeResponse(
    val color: RecordColor,
    val style: RecordStyle,
)