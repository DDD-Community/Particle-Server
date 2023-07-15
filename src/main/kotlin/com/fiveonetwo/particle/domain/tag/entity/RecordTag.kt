package com.fiveonetwo.particle.domain.tag.entity

import com.fiveonetwo.particle.shared.utils.uuid
import org.springframework.data.annotation.Id
import org.springframework.data.domain.Persistable
import org.springframework.data.relational.core.mapping.Column
import org.springframework.data.relational.core.mapping.Table


@Table("record_tags")
class RecordTag(
    @Id
    @Column("record_tag_id")
    private var id: String = "-1",
    val value: RecordTagValue,
    val recordId: String,
) : Persistable<String> {
    override fun getId(): String = id

    override fun isNew(): Boolean {
        val isNew = id == "-1"
        id = uuid()
        return isNew
    }
}

enum class RecordTagValue {
    UXUI, BRANDING, MARCKETING, DEVELOPMENT
}