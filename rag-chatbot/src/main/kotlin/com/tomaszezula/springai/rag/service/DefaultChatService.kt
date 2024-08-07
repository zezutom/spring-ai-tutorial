package com.tomaszezula.springai.rag.service

import com.tomaszezula.springai.rag.ServiceResponse
import org.slf4j.LoggerFactory
import org.springframework.ai.chat.prompt.Prompt
import org.springframework.ai.model.function.FunctionCallback
import org.springframework.ai.openai.OpenAiChatModel
import org.springframework.ai.openai.OpenAiChatOptions
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux

@Service
class DefaultChatService(
    private val model: OpenAiChatModel,
    private val currentWeatherFunction: FunctionCallback
) : IChatService {
    private val logger = LoggerFactory.getLogger(this::class.java)

    override fun getResponse(message: String): ServiceResponse<String> {
        return try {
            val response = model.call(prompt(message))?.result?.output?.content ?: run {
                return ServiceResponse.Failure("Sorry, I can't help you right now")
            }
            ServiceResponse.Success(response)
        } catch (e: Exception) {
            logger.error("Failed to get response", e)
            ServiceResponse.Failure("Sorry, I can't help you right now")
        }
    }

    override fun streamResponse(message: String): Flux<ServiceResponse<String>> {
        return model.stream(
            prompt(message)
        ).mapNotNull<ServiceResponse<String>> {
            it?.result?.output?.content?.let { content ->
                ServiceResponse.Success(content)
            }
        }.onErrorResume { e ->
            logger.error("Failed to get response", e)
            Flux.just(ServiceResponse.Failure("Sorry, I can't help you right now"))
        }
    }

    private fun prompt(message: String) = Prompt(
        message,
        OpenAiChatOptions.builder()
            .withFunctionCallbacks(listOf(currentWeatherFunction))
            .build()
    )
}

