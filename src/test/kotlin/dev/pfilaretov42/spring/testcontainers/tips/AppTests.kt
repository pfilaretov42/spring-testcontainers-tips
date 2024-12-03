package dev.pfilaretov42.spring.testcontainers.tips

import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.annotation.Import

@Import(TestcontainersConfiguration::class)
@SpringBootTest
class AppTests {

    @Test
    fun contextLoads() {
    }

}
