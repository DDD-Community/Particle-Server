package com.fiveonetwo.particle.domain.record.entity

import com.fiveonetwo.particle.domain.common.entity.BaseTimeEntity
import com.fiveonetwo.particle.domain.user.entity.User
import com.fiveonetwo.particle.shared.utils.uuid
import jakarta.persistence.CascadeType
import jakarta.persistence.Column
import jakarta.persistence.ConstraintMode
import jakarta.persistence.Embeddable
import jakarta.persistence.Embedded
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.FetchType
import jakarta.persistence.ForeignKey
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.OneToMany
import jakarta.persistence.OneToOne
import jakarta.persistence.Table

@Entity
@Table(name = "records")
class Record(
    @Id
    @Column(name = "record_id", nullable = false)
    val id: String = uuid(),
    @ManyToOne(targetEntity = User::class, fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    val user: User,
    @Column(name = "title", nullable = false, columnDefinition = "text")
    var title: String,
    @Column(name = "url", nullable = false, columnDefinition = "text")
    var url: String,
    @Embedded
    var attribute: RecordAttribute = RecordAttribute(),
    @OneToMany(targetEntity = RecordItem::class, mappedBy = "record", fetch = FetchType.EAGER, cascade = [CascadeType.ALL])
    var items: MutableList<RecordItem> = mutableListOf(),
    @OneToMany(targetEntity = RecordTag::class, mappedBy = "record", fetch = FetchType.EAGER, cascade = [CascadeType.ALL])
    var tags: MutableList<RecordTag> = mutableListOf(),
) : BaseTimeEntity() {
    fun update(
        title: String = this.title,
        url: String = this.url,
        tags: MutableList<RecordTag> = this.tags,
        items: MutableList<RecordItem> = this.items,
        style: RecordStyle = this.attribute.style,
        color: RecordColor = this.attribute.color,
    ) {
        this.title = title
        this.url = url
        this.tags = tags
        this.items = items
        this.attribute.style = style
        this.attribute.color = color
    }

    fun mainItems(): List<RecordItem> = items.filter { item -> item.isMain }
}

fun randomColor(): RecordColor = RecordColor.values().random()

@Embeddable
class RecordAttribute(
    @Column(name = "color")
    @Enumerated(value = EnumType.STRING)
    var color: RecordColor = randomColor(),
    @Column(name = "style")
    @Enumerated(value = EnumType.STRING)
    var style: RecordStyle = RecordStyle.CARD
)

enum class RecordColor {
    YELLOW, BLUE
}

enum class RecordStyle {
    TEXT, CARD
}

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
    val value: Tag,
)

enum class Tag(
    val value: String
) {
    BRANDING("브랜딩"),
    UXUI("UXUI"),
    GRAPHIC("그래픽 디자인"),
    INDUSTRY("산업 디자인"),

    IOS("iOS"),
    ANDROID("Android"),
    WEB("Web"),
    SERVER("서버"),
    AI("AI"),

    SERVICE_PLAN("서비스 기획"),
    STRATEGY_PLAN("전략 기획"),
    SYSTEM_PLAN("시스템 기획"),
    DATA_ANALYZE("데이터 분석"),

    COMPANY_CULTURE("조직 문화"),
    TREND("트렌드"),
    CX("CX"),
    LEADERSHIP("리더쉽"),
    INSIGHT("인사이트"),

    BRANDING_MARKETING("브랜드 마케팅"),
    GROWTH_MARKETING("그로스 마케팅"),
    CONTENTS_MARKETING("콘텐츠 마케팅");

    companion object {
        fun originalValueOf(value: String): Tag {
            for (tag in values()) {
                if (tag.value == value) {
                    return tag
                }
            }
            throw IllegalArgumentException("tag value($value) is incorrect")
        }
    }
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

@Table(name = "record_search")
@Entity
class RecordSearch(
    @Id
    @Column(name = "record_search_id")
    val id: String = uuid(),
    @OneToOne(fetch = FetchType.LAZY, targetEntity = Record::class)
    @JoinColumn(name = "record_id", nullable = false, foreignKey = ForeignKey(value = ConstraintMode.NO_CONSTRAINT))
    val record: Record,
    @Column(name = "total_content", nullable = false)
    val totalContent: String,
)