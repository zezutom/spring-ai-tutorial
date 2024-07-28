package com.tomaszezula.springai.rag.service

import org.springframework.ai.chat.client.RequestResponseAdvisor
import org.springframework.ai.chat.model.ChatResponse
import reactor.core.publisher.Flux

class WeatherAdvisor : RequestResponseAdvisor {
    override fun adviseResponse(
        fluxResponse: Flux<ChatResponse>?,
        context: MutableMap<String, Any>?,
    ): Flux<ChatResponse> {
        fluxResponse?.doOnNext { response ->
            ChatResponse(listOf())
        }
        return super.adviseResponse(fluxResponse, context)
    }
}
