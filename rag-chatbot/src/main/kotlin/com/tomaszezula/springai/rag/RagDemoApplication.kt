package com.tomaszezula.springai.rag

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class RagDemoApplication

fun main(args: Array<String>) {
    runApplication<RagDemoApplication>(*args)
}
