package com.fiveonetwo.particle.domain.record.entity

import com.fiveonetwo.particle.shared.utils.uuid
import org.springframework.data.annotation.Id
import org.springframework.data.domain.Persistable
import org.springframework.data.relational.core.mapping.Column
import org.springframework.data.relational.core.mapping.Table
import java.time.LocalDateTime

@Table("records")
class Record(
    @Id
    @Column("record_id")
    private var id: String = "-1",
    val userId: String,
    val title: String,
    val url: String,
    val createdBy: String,
    val createdAt: LocalDateTime = LocalDateTime.now(),
) : Persistable<String> {
    override fun getId(): String = id
    override fun isNew(): Boolean {
        val isNew = id == "-1"
        id = uuid()
        return isNew
    }
}


@Table("record_items")
class RecordItem(
    @Id
    @Column("record_item_id")
    private var id: String = "-1",
    val recordId: String,
    val content: String,
    val isMain: Boolean = false,
) : Persistable<String> {
    override fun getId(): String = id

    override fun isNew(): Boolean {
        val isNew = id == "-1"
        id = uuid()
        return isNew
    }
}