package com.fiveonetwo.particle.web.record

import com.fiveonetwo.particle.domain.record.dto.RecordCreateDTO
import com.fiveonetwo.particle.domain.record.dto.RecordReadDTO
import com.fiveonetwo.particle.domain.record.service.RecordService
import kotlinx.coroutines.reactor.awaitSingle
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.function.server.awaitBody
import org.springframework.web.reactive.function.server.bodyValueAndAwait

@Component
class RecordHandler(
    private val recordService: RecordService,
) {
    suspend fun createRecord(request: ServerRequest): ServerResponse =
        recordService.createRecord(request.principal().awaitSingle().name, request.awaitBody<RecordCreateDTO>())
            .let { ServerResponse.ok().bodyValueAndAwait(it) }

    suspend fun getRecordById(request: ServerRequest): ServerResponse =
        recordService.getRecordById(request.pathVariable("recordId"))
            .let { ServerResponse.ok().bodyValueAndAwait(it) }

    suspend fun getMyRecords(request: ServerRequest): ServerResponse =
        recordService.getMyRecords(request.principal().awaitSingle().name)
            .let { ServerResponse.ok().bodyValueAndAwait(it) }
}