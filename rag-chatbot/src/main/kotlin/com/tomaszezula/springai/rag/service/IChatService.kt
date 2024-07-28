package com.tomaszezula.springai.rag.service

import com.tomaszezula.springai.rag.ServiceResponse
import reactor.core.publisher.Flux

interface IChatService {
    fun getResponse(message: String): ServiceResponse<String>
    fun streamResponse(message: String): Flux<ServiceResponse<String>>
}
