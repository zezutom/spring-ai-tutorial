package com.tomaszezula.springai.rag

import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.tomaszezula.springai.rag.service.WeatherServiceFunction
import com.tomaszezula.springai.rag.service.WeatherServiceImpl
import org.springframework.ai.model.function.FunctionCallback
import org.springframework.ai.model.function.FunctionCallbackWrapper
import org.springframework.ai.openai.OpenAiChatModel
import org.springframework.ai.openai.OpenAiChatOptions
import org.springframework.ai.openai.api.OpenAiApi
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
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
    }

    @Bean
    fun advisedChatModel(): OpenAiChatModel {
        val api = OpenAiApi(System.getenv("OPENAI_API_KEY"))
        val options =  OpenAiChatOptions.builder()
            .withModel("gpt-3.5-turbo")
            .withTemperature(0.4F)
            .build()
        return OpenAiChatModel(api, options)
    }

    @Bean
    fun currentWeatherFunction(): FunctionCallback {
        return FunctionCallbackWrapper.builder(WeatherServiceFunction(WeatherServiceImpl()))
            .withName("CurrentWeather")
            .withDescription("Get current weather forecast")
            .build()
    }
}
