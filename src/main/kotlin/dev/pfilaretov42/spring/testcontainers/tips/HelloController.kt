package dev.pfilaretov42.spring.testcontainers.tips

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Service
import org.springframework.web.bind.annotation.*
import java.util.*

// TODO - rename hello to something
@RestController
@RequestMapping("/hello")
class HelloController(
    private val helloService: HelloService,
) {

    @GetMapping
    fun getAll(): List<String> {
        return helloService.getAll().map { "${it.name}: ${it.id}" }
    }

    @PostMapping
    fun addHello(@RequestBody dto: HelloDto): Unit {
        helloService.addHello(dto.name)
    }
}

class HelloDto(val name: String)

@Service
class HelloService(
    private val helloRepository: HelloRepository,
) {

    fun addHello(name: String) {
        helloRepository.save(HelloEntity(name = name))
    }

    // Returns entity objects instead of DTO here for simplicity
    fun getAll(): List<HelloEntity> {
        return helloRepository.findAll().toList()
    }
}

interface HelloRepository : CrudRepository<HelloEntity, Int>

@Table("hello")
class HelloEntity(
    /**
     * [id] is auto generated in the DB.
     * Spring Data JDBC will perform add during [save()] if [id] is [null] or [id] == 0.
     * Otherwise, it will perform [update()].
     */
    @Id
    val id: UUID? = null,

    val name: String,
)