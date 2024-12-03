package dev.pfilaretov42.spring.testcontainers.tips

import org.springframework.boot.fromApplication
import org.springframework.boot.with


fun main(args: Array<String>) {
    fromApplication<App>().with(TestcontainersConfiguration::class).run(*args)
}
