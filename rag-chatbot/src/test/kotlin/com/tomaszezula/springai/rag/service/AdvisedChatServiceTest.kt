package com.tomaszezula.springai.rag.service

import com.tomaszezula.springai.rag.call
import com.tomaszezula.springai.rag.containsTemperature
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import kotlin.test.assertTrue

@SpringBootTest
class AdvisedChatServiceTest {

    @Autowired
    private lateinit var chatService: AdvisedChatService

    @Test
    fun `should estimate temperature according to location and time`() {
        verifyEstimatedTemperature("Austin", "31 July 2024", "03:00", 80)
        verifyEstimatedTemperature("Austin",  "2 Aug 24", "08:00", 77)
    }

    private fun verifyEstimatedTemperature(location: String, day: String, time: String, expectedTemperature: Short, expectedUnit: TempUnit = TempUnit.FAHRENHEIT) {
        val response = call { chatService.getResponse("What's the temperature in $location on $day at $time?") }
        assertTrue(response.containsTemperature(expectedTemperature, expectedUnit), "Unexpected response: $response")
    }
}
