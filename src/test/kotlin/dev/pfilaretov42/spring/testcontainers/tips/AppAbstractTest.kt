package dev.pfilaretov42.spring.testcontainers.tips

import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.BeforeEach
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.boot.test.web.server.LocalServerPort
import org.springframework.test.context.DynamicPropertyRegistry
import org.springframework.test.context.DynamicPropertySource
import org.testcontainers.containers.PostgreSQLContainer
import org.testcontainers.utility.DockerImageName

//@Import(TestcontainersConfiguration::class)
//@ActiveProfiles("test")
//@ContextConfiguration(classes = [TestcontainersConfiguration::class])
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
abstract class AppAbstractTest {

    @LocalServerPort
    protected var port: Int = 0

    @Autowired
    protected lateinit var testRestTemplate: TestRestTemplate

    protected lateinit var helloEndpointUrl: String

    @BeforeEach
    fun setUp() {
        helloEndpointUrl = "http://localhost:$port/hello"
    }

    companion object {
        private val postgresContainer = PostgreSQLContainer(DockerImageName.parse("postgres:17"))

        init {
            println("=== STARTING CONTAINER ===")
            postgresContainer.start()
        }

//        @JvmStatic
//        @BeforeAll
//        fun setUp() {
//            println("=== STARTING CONTAINER ===")
//            postgresContainer.start()
//        }

        @JvmStatic
        @DynamicPropertySource
        fun setApplicationProperties(registry: DynamicPropertyRegistry) {
            registry.add("spring.datasource.url") { postgresContainer.jdbcUrl }
            registry.add("spring.datasource.username") { postgresContainer.username }
            registry.add("spring.datasource.password") { postgresContainer.password }
        }

        @JvmStatic
        @AfterAll
        fun tearDown() {
            println("=== STOPPING CONTAINER ===")
            postgresContainer.stop()
        }
    }
}

