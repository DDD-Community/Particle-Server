package com.fiveonetwo.particle.domain.record.entity

import com.fiveonetwo.particle.domain.common.entity.BaseTimeEntity
import com.fiveonetwo.particle.domain.user.entity.User
import com.fiveonetwo.particle.shared.utils.uuid
import jakarta.persistence.CascadeType
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.FetchType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.OneToMany
import jakarta.persistence.Table

@Table(name = "records")
@Entity
class Record(
        @Id
        @Column(name = "record_id", nullable = false)
        val id: String = uuid(),
        @ManyToOne(targetEntity = User::class, fetch = FetchType.LAZY)
        @JoinColumn(name = "user_id", nullable = false)
        val user: User,
        @Column(name = "title", nullable = false, columnDefinition = "text")
        val title: String,
        @Column(name = "url", nullable = false, columnDefinition = "text")
        val url: String,
        @OneToMany(targetEntity = RecordItem::class, mappedBy = "record", fetch = FetchType.LAZY, cascade = [CascadeType.ALL])
        val items: MutableList<RecordItem> = mutableListOf(),
        @OneToMany(targetEntity = RecordTag::class, mappedBy = "record", fetch = FetchType.LAZY, cascade = [CascadeType.ALL])
        val tags: MutableList<RecordTag> = mutableListOf(),
) : BaseTimeEntity()

@Table(name = "record_tags")
@Entity
class RecordTag(
        @Id
        @Column(name = "record_tag_id", nullable = false)
        val id: String = uuid(),
        @ManyToOne(targetEntity = Record::class, fetch = FetchType.LAZY)
        @JoinColumn(name = "record_id", nullable = false)
        val record: Record,
        @Enumerated(value = EnumType.STRING)
        @Column(name = "value", nullable = false)
        val value: RecordTagValue,
)

enum class RecordTagValue {
    UXUI, DEVELOPEMENT, DESIGN, MARKETING
}

@Table(name = "record_items")
@Entity
class RecordItem(
        @Id
        @Column(name = "record_item_id", nullable = false)
        val id: String = uuid(),
        @ManyToOne(targetEntity = Record::class, fetch = FetchType.LAZY)
        @JoinColumn(name = "record_id", nullable = false)
        val record: Record,
        @Column(name = "content", nullable = false, columnDefinition = "text")
        val content: String,
        @Column(name = "is_main", nullable = false)
        val isMain: Boolean = false,
)