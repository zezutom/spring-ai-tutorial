# Spring AI Tutorial
This is a collection of examples for AI engineering using Spring Boot, Java and Kotlin.

## Table of Contents
- [Function Calling](#function-calling)

## Function Calling

Spring AI supports enriching the generic model with additional data coming from custom functions. 
This technique known as __function calling__ allows to elegantly tap into a variety of external data sources. 
However, it comes with its own challenges and considerations.

Blog post: [Spring AI and Challenges with Function Calling](https://www.tomaszezula.com/spring-ai-and-challenges-with-function-calling/)

Implementation:
- [Chat Service](rag-chatbot/src/main/kotlin/com/tomaszezula/springai/rag/service/DefaultChatService.kt)
- Function Calling
  - [Implementation](rag-chatbot/src/main/kotlin/com/tomaszezula/springai/rag/service/WeatherService.kt)
  - [Configuration](rag-chatbot/src/main/kotlin/com/tomaszezula/springai/rag/Config.kt)
- [Tests](rag-chatbot/src/test/kotlin/com/tomaszezula/springai/rag/service/DefaultChatServiceTest.kt)
