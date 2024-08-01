package com.tomaszezula.springai.rag.etl

import com.fasterxml.jackson.databind.ObjectMapper
import org.slf4j.LoggerFactory
import org.springframework.ai.document.Document
import org.springframework.ai.vectorstore.RedisVectorStore
import org.springframework.ai.vectorstore.VectorStore
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import java.io.File

@Component
class DataLoader(
    @Value("\${rag.etl.source-dir}")
    private val sourceDir: File,
    private val objectMapper: ObjectMapper,
    val vectorStore: VectorStore,
) {
    private val logger = LoggerFactory.getLogger(this::class.java)

    fun init() {
        if (sourceDir.exists().not()) {
            logger.warn("Directory does not exist: $sourceDir")
            return
        }
        cleanDatastore()
        loadDocuments(sourceDir)
    }

    private fun cleanDatastore() {
        logger.info("Cleaning datastore")
        val jedis = (vectorStore as RedisVectorStore).jedis
        val keys = jedis.keys("*")
        logger.info("Deleting ${keys.size} keys")
        keys.forEach { key -> jedis.del(key) }
        logger.info("Deletion completed.")
    }

    private fun loadDocuments(directory: File) {
        if (directory.isDirectory.not()) {
            logger.warn("Not a directory: $directory")
            return
        }
        val files = directory.listFiles() ?: return
        files.forEach { file ->
            if (file.isDirectory) {
                loadDocuments(file)
            } else {
                processFile(file)
            }
        }
    }

    private fun processFile(file: File) {
        when (file.extension) {
            "json" -> {
                // Load weather data from JSON file
                logger.info("Loading weather data from: ${file.absolutePath}")
                val forecast = objectMapper.readValue(file, HourlyForecast::class.java)
                val documents = forecast.properties.periods.chunked(50).map { periods ->
                    val json = objectMapper.writeValueAsString(periods)
                    Document(json, forecast.metadata)
                }
                vectorStore.add(documents)
            }

            else -> logger.warn("Unsupported file format: ${file.absolutePath}")
        }
    }
}
