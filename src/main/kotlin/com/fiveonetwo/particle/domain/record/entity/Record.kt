package com.fiveonetwo.particle.domain.record.entity

import com.fiveonetwo.particle.domain.user.entity.User
import com.fiveonetwo.particle.shared.utils.uuid
import jakarta.persistence.*
import org.springframework.data.annotation.CreatedDate
import java.time.LocalDateTime

@Table(name = "records")
@Entity
class Record(
        @Id
        @Column(name = "record_id")
        val id: String = uuid(),
        @ManyToOne(targetEntity = User::class, fetch = FetchType.LAZY)
        @JoinColumn(name = "user_id")
        val user: User,
        @Column(name = "title", columnDefinition = "text")
        val title: String,
        @Column(name = "url", columnDefinition = "text")
        val url: String,
        @CreatedDate
        @Column(name = "created_at")
        val createdAt: LocalDateTime = LocalDateTime.now(),
)

@Table(name = "record_tags")
@Entity
class RecordTag(
        @Id
        @Column(name = "record_tag_id")
        val id: String = uuid(),
        @ManyToOne(targetEntity = Record::class, fetch = FetchType.LAZY)
        @JoinColumn(name = "record_id")
        val record: Record,
        @Enumerated(value = EnumType.STRING)
        val value: RecordTagValue,
)

enum class RecordTagValue {
    UXUI, DEVELOPEMENT, DESIGN, MARKETING
}

@Table(name = "record_items")
@Entity
class RecordItem(
        @Id
        @Column(name = "record_item_id")
        val id: String = uuid(),
        @ManyToOne(targetEntity = Record::class, fetch = FetchType.LAZY)
        @JoinColumn(name = "record_id")
        val record: Record,
        @Column(name = "content", columnDefinition = "text")
        val content: String,
        val isMain: Boolean = false,
)