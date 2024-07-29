package com.tomaszezula.springai.rag.service

import com.tomaszezula.springai.rag.call
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import kotlin.test.Test
import kotlin.test.assertTrue

@SpringBootTest
class DefaultChatServiceTest {

    @Autowired
    private lateinit var chatService: DefaultChatService

    @Test
    fun `should return the expected response`() {
        val response = call { chatService.getResponse("What's the temperature in San Francisco?") }
        assertTrue { response.contains("25.0°C") }
    }

    @Test
    fun `should stream the expected response`() {
        val list = chatService.streamResponse("What's the temperature in San Francisco?").map {
            call { it }
        }.collectList().block() ?: emptyList()
        val response = list.joinToString("")
        assertTrue { response.contains("25.0°C") }
    }
}
