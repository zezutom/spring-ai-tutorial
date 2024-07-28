package com.tomaszezula.springai.rag

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.tomaszezula.springai.rag.service.WeatherServiceFunction
import com.tomaszezula.springai.rag.service.WeatherServiceImpl
import org.springframework.ai.chat.client.ChatClient
import org.springframework.ai.model.function.FunctionCallback
import org.springframework.ai.model.function.FunctionCallbackWrapper
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Primary
import org.springframework.web.client.RestClient

@Configuration
class Config {

    @Bean
    fun restClientBuilder(): RestClient.Builder {
        return RestClient.builder()
    }

    @Bean
    @Primary
    fun objectMapper(): ObjectMapper {
        return jacksonObjectMapper()
    }

    @Bean
    fun currentWeatherFunction(): FunctionCallback {
        return FunctionCallbackWrapper.builder(WeatherServiceFunction(WeatherServiceImpl()))
            .withName("CurrentWeather")
            .withDescription("Get current weather forecast")
            .build()
    }
}
