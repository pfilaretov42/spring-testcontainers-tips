package dev.pfilaretov42.spring.testcontainers.tips

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class AnotherApiTest : AppAbstractTest() {
    @Test
    fun `test stop container`() {
        println("another test")
        val list = testRestTemplate.getForObject(helloEndpointUrl, List::class.java)
        assertThat(list.isEmpty())
    }
}