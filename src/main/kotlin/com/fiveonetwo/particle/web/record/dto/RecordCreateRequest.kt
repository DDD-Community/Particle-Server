package com.fiveonetwo.particle.web.record.dto

import com.fiveonetwo.particle.domain.record.dto.RecordItemCreateDTO
import com.fiveonetwo.particle.domain.record.entity.RecordStyle

class RecordCreateRequest(
    val title: String,
    val url: String,
    val items: List<RecordItemCreateDTO>,
    val tags: List<String>,
    val style: RecordStyle = RecordStyle.TEXT,
)