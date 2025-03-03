package dev.pfilaretov42.spring.testcontainers.tips

import io.github.oshai.kotlinlogging.KotlinLogging
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.boot.test.web.client.postForObject

private val logger = KotlinLogging.logger {}

class SilmarilliApiTest : AppAbstractTest() {

    protected lateinit var endpointUrl: String

    @BeforeEach
    fun setUp() {
        endpointUrl = "http://localhost:$port/silmarilli"
        silmarilTreasury.deleteAll()
    }

    @Test
    fun `should craft silmarilli`() {
        logger.info { "TEST: should craft silmarilli" }
        testRestTemplate.postForObject<Unit>(endpointUrl, null)
        val list = testRestTemplate.getForObject(endpointUrl, List::class.java)
        assertThat(list.size).isEqualTo(3)
    }
}