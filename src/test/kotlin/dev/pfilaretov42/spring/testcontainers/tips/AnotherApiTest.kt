package dev.pfilaretov42.spring.testcontainers.tips

import io.github.oshai.kotlinlogging.KotlinLogging
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

private val logger = KotlinLogging.logger {}

class AnotherApiTest : AppAbstractTest() {
    @Test
    fun `should return empty list`() {
        logger.info { "TEST: should return empty list" }
        val list = testRestTemplate.getForObject(helloEndpointUrl, List::class.java)
        assertThat(list.isEmpty())
    }
}