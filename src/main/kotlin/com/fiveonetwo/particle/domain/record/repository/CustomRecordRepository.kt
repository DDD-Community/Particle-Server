package com.fiveonetwo.particle.domain.record.repository

import com.fiveonetwo.particle.domain.record.entity.Record
import com.fiveonetwo.particle.domain.record.entity.Tag
import com.fiveonetwo.particle.domain.user.entity.User
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable

interface CustomRecordRepository {
    fun pagedOtherPersonRecordsByTags(pageable: Pageable, tags: List<Tag>, currentUser : User) : Page<Record>
}