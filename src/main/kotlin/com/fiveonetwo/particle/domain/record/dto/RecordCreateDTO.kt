package com.fiveonetwo.particle.domain.record.dto

import com.fiveonetwo.particle.domain.record.entity.Record
import com.fiveonetwo.particle.domain.record.entity.RecordItem
import com.fiveonetwo.particle.domain.record.entity.RecordTag
import com.fiveonetwo.particle.domain.record.entity.RecordTagValue
import com.fiveonetwo.particle.domain.user.entity.User

class RecordCreateDTO(
        val title: String,
        val url: String,
        val items: List<RecordItemCreateDTO>,
        val tags: List<RecordTagValue>,
) {
    fun toRecord(user: User): Record {
        val record = Record(
                title = title,
                url = url,
                user = user
        )
        items.forEach { item -> record.items.add(item.toRecordItem(record)) }
        tags.forEach { tag -> record.tags.add(RecordTag(record = record, value = tag)) }
        return record
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