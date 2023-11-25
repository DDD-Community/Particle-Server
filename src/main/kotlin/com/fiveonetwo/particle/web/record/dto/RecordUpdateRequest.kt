package com.fiveonetwo.particle.web.record.dto

import com.fiveonetwo.particle.domain.record.entity.RecordStyle

class RecordUpdateRequest(
    val title: String,
    val url: String,
    val items: List<RecordItemUpdateRequest>,
    val tags: List<String>,
    val style: RecordStyle,
)

class RecordItemUpdateRequest(
    val content: String,
    val isMain: Boolean,
)