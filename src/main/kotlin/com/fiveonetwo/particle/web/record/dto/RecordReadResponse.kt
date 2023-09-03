package com.fiveonetwo.particle.web.record.dto

import com.fiveonetwo.particle.domain.record.dto.RecordReadDTO
import java.time.LocalDateTime

class RecordReadResponse(
    val title: String,
    val url: String,
    val items: List<RecordItemCreateResponse>,
    val tags: List<String>,
    val createdAt: LocalDateTime,
    val createdBy: String,
) {
    companion object {
        fun from(dto: RecordReadDTO): RecordReadResponse {
            return RecordReadResponse(
                title = dto.title,
                url = dto.url,
                items = dto.items.map { item ->
                    RecordItemCreateResponse(
                        content = item.content,
                        isMain = item.isMain
                    )
                },
                tags = dto.tags.map { tag -> tag.value },
                createdAt = dto.createdAt,
                createdBy = dto.createdBy
            )
        }
    }
}

class RecordItemCreateResponse(
    val content: String,
    val isMain: Boolean,
)

