package com.tomaszezula.springai.rag.etl

data class HourlyForecast(
    val metadata: Map<String, String>,
    val properties: Properties
)

data class Properties(
    val periods: List<TimePeriod>
)

data class TimePeriod(
    val startTime: String,
    val endTime: String,
    val temperature: Int,
    val temperatureUnit: String,
)
