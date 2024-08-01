package com.tomaszezula.springai.rag

import com.tomaszezula.springai.rag.service.TempUnit

fun String.containsTemperature(temperature: Short, unit: TempUnit): Boolean {
    return this.contains("$temperature°${unit.symbol}") || this.contains("$temperature degrees ${unit.description}")
}
