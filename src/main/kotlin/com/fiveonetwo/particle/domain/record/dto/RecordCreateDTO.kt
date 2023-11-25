package com.fiveonetwo.particle.domain.record.dto

import com.fiveonetwo.particle.domain.record.entity.Record
import com.fiveonetwo.particle.domain.record.entity.RecordAttribute
import com.fiveonetwo.particle.domain.record.entity.RecordItem
import com.fiveonetwo.particle.domain.record.entity.RecordStyle
import com.fiveonetwo.particle.domain.record.entity.RecordTag
import com.fiveonetwo.particle.domain.record.entity.Tag
import com.fiveonetwo.particle.domain.user.entity.User
import com.fiveonetwo.particle.web.record.dto.RecordCreateRequest

class RecordCreateDTO(
    val title: String,
    val url: String,
    val items: List<RecordItemCreateDTO>,
    val tags: List<Tag>,
    val style: RecordStyle,
) {
    fun toRecord(user: User): Record {
        val record = Record(
            title = title,
            url = url,
            attribute = RecordAttribute(style = style),
            user = user
        )
        items.forEach { item -> record.items.add(item.toRecordItem(record)) }
        tags.forEach { tag -> record.tags.add(RecordTag(record = record, value = tag)) }
        return record
    }

    companion object {
        fun from(request: RecordCreateRequest): RecordCreateDTO = RecordCreateDTO(
            title = request.title,
            url = request.url,
            items = request.items.map { item -> RecordItemCreateDTO(content = item.content, isMain = item.isMain) },
            tags = request.tags.map { tag -> Tag.originalValueOf(tag) },
            style = request.style,
        )
    }
}

class RecordItemCreateDTO(
    val content: String,
    val isMain: Boolean,
) {
    fun toRecordItem(record: Record): RecordItem {
        return RecordItem(
            content = content,
            isMain = isMain,
            record = record
        )
    }
}