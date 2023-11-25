package com.fiveonetwo.particle.domain.record.dto

import com.fiveonetwo.particle.domain.record.entity.Record
import com.fiveonetwo.particle.domain.record.entity.RecordAttribute
import com.fiveonetwo.particle.domain.record.entity.RecordItem
import com.fiveonetwo.particle.domain.record.entity.RecordStyle
import com.fiveonetwo.particle.domain.record.entity.RecordTag
import com.fiveonetwo.particle.domain.record.entity.Tag
import com.fiveonetwo.particle.domain.user.entity.User
import com.fiveonetwo.particle.web.record.dto.RecordUpdateRequest


class RecordUpdateDTO(
    val title: String,
    val url: String,
    val items: List<RecordItemUpdateDTO>,
    val tags: List<Tag>,
    val style: RecordStyle,
) {
    companion object {
        fun from(record: Record) = RecordUpdateDTO(
            title = record.title,
            url = record.url,
            items = record.items.map { RecordItemUpdateDTO(it.content, it.isMain) },
            tags = record.tags.map { it.value },
            style = record.attribute.style
        )

        fun from(request: RecordUpdateRequest): RecordUpdateDTO = RecordUpdateDTO(
            title = request.title,
            url = request.url,
            items = request.items.map { item -> RecordItemUpdateDTO(content = item.content, isMain = item.isMain) },
            tags = request.tags.map { tag -> Tag.originalValueOf(tag) },
            style = request.style,
        )
    }

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
}

class RecordItemUpdateDTO(
    val content: String,
    val isMain: Boolean
) {
    fun toRecordItem(record: Record): RecordItem {
        return RecordItem(
            content = content,
            isMain = isMain,
            record = record
        )
    }
}