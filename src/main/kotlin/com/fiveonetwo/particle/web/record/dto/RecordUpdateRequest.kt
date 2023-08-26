package com.fiveonetwo.particle.web.record.dto

class RecordUpdateRequest(
    val title: String,
    val url: String,
    val items: List<RecordItemUpdateRequest>,
    val tags: List<String>,
)

class RecordItemUpdateRequest(
    val content: String,
    val isMain: Boolean,
)