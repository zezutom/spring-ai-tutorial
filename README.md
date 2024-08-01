# Spring AI Tutorial
This is a collection of examples for AI engineering using Spring Boot, Java and Kotlin.

## Table of Contents
- [Function Calling](#function-calling)
- [Retrieval Augmented Generation](#retrieval-augmented-generation)

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

## Retrieval Augmented Generation

Data sets:
1. What's the sunniest place in the US? Let's find out!
   - [Comparative Climatic Data, 2015 (pdf)](https://www1.ncdc.noaa.gov/pub/data/ccd-data/CCD-2015.pdf)
2. Weather forecast in various US cities
- [New York](https://api.weather.gov/gridpoints/OKX/33,35/forecast/hourly)
- [Los Angeles](https://api.weather.gov/gridpoints/LOX/155,45/forecast/hourly)
- [Austin](https://api.weather.gov/gridpoints/EWX/156,91/forecast/hourly)
- [Chicago](https://api.weather.gov/gridpoints/LOT/76,73/forecast/hourly)
