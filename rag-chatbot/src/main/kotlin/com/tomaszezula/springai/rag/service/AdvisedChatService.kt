package com.tomaszezula.springai.rag.service

import com.tomaszezula.springai.rag.ServiceResponse
import com.tomaszezula.springai.rag.etl.DataLoader
import org.slf4j.LoggerFactory
import org.springframework.ai.chat.client.ChatClient
import org.springframework.ai.chat.client.advisor.QuestionAnswerAdvisor
import org.springframework.ai.openai.OpenAiChatModel
import org.springframework.ai.vectorstore.SearchRequest
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux

@Service
class AdvisedChatService(
    @Qualifier("advisedChatModel")
    private val model: OpenAiChatModel,
    private val vectorStoreFactory: DataLoader
) : IChatService {
    private val logger = LoggerFactory.getLogger(this::class.java)

    init {
        // Populate weather data into the vector store
        vectorStoreFactory.init()
    }

    override fun getResponse(message: String): ServiceResponse<String> {
        return try {
            val content = ChatClient.builder(model).build().prompt()
                .advisors(QuestionAnswerAdvisor(vectorStoreFactory.vectorStore, SearchRequest.query(message)))
                .user(message)
                .call()
                .content()
            ServiceResponse.Success(content)
        } catch (e: Exception) {
            logger.error("Failed to get response", e)
            ServiceResponse.Failure("Sorry, I can't help you right now")
        }
    }

    override fun streamResponse(message: String): Flux<ServiceResponse<String>> {
        return ChatClient.builder(model).build().prompt()
            .advisors(QuestionAnswerAdvisor(vectorStoreFactory.vectorStore, SearchRequest.defaults()))
            .user(message)
            .stream()
            .content().mapNotNull<ServiceResponse<String>> {
                ServiceResponse.Success(it)
            }.onErrorResume { e ->
                logger.error("Failed to get response", e)
                Flux.just(ServiceResponse.Failure("Sorry, I can't help you right now"))
            }
    }
}
