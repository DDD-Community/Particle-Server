package com.fiveonetwo.particle.domain.record.repository

import com.fiveonetwo.particle.domain.record.entity.QRecord.record
import com.fiveonetwo.particle.domain.record.entity.QRecordTag.recordTag
import com.fiveonetwo.particle.domain.record.entity.Record
import com.fiveonetwo.particle.domain.record.entity.Tag
import com.fiveonetwo.particle.domain.record.error.RecordNotFoundException
import com.fiveonetwo.particle.domain.user.entity.User
import com.querydsl.jpa.impl.JPAQueryFactory
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Repository

@Repository
class CustomRecordRepositoryImpl(
    val jpaQueryFactory: JPAQueryFactory,
) : CustomRecordRepository {
    override fun pagedOtherPersonRecordsByTags(pageable: Pageable, tags: List<Tag>, currentUser: User): Page<Record> {
        val pageSize = pageable.pageSize.toLong()
        val offset = pageable.offset

        val content = jpaQueryFactory.selectFrom(record)
            .leftJoin(recordTag).on(record.eq(recordTag.record))
            .where(recordTag.value.`in`(tags), record.user.ne(currentUser))
            .offset(offset)
            .limit(pageSize)
            .orderBy(record.createdAt.desc())
            .fetch() ?: throw RecordNotFoundException()

        val count = jpaQueryFactory.select(record.count())
            .from(record)
            .leftJoin(recordTag).on(record.eq(recordTag.record))
            .where(recordTag.value.`in`(tags), record.user.ne(currentUser))
            .offset(offset)
            .limit(pageSize)
            .fetchOne() ?: throw RecordNotFoundException()

        return PageImpl(content, pageable, count)
    }
}