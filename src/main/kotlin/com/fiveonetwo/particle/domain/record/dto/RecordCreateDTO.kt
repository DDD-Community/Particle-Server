package com.fiveonetwo.particle.domain.record.dto

import com.fiveonetwo.particle.domain.record.entity.Record
import com.fiveonetwo.particle.domain.record.entity.RecordItem
import com.fiveonetwo.particle.domain.tag.entity.RecordTag
import com.fiveonetwo.particle.domain.tag.entity.RecordTagValue
import com.fiveonetwo.particle.domain.user.entity.User


class RecordCreateDTO(
    val title: String,
    val url: String,
    val items: List<RecordItemCreateDTO>,
    val tags: List<RecordTagValue>,
) {
    fun toRecord(user: User): Record {
        return Record(
            title = title,
            url = url,
            userId = user.id,
            createdBy = user.nickname,
        )
    }

    fun toRecordItems(recordId: String): MutableList<RecordItem> = items.map {
        RecordItem(
            recordId = recordId,
            content = it.content,
            isMain = it.isMain,
        )
    }.toMutableList()

    fun toRecordTags(recordId: String): MutableList<RecordTag> = tags.map {
        RecordTag(
            value = it,
            recordId = recordId,
        )
    }.toMutableList()
}

class RecordItemCreateDTO(
    val content: String,
    val isMain: Boolean,
)