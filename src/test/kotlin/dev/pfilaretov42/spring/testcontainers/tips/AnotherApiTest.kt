package dev.pfilaretov42.spring.testcontainers.tips

import org.junit.jupiter.api.Test

class AnotherApiTest : AppAbstractTest() {
    @Test
    fun `test stop container`() {
        println("another test")
    }
}