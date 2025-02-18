package dev.pfilaretov42.spring.testcontainers.tips

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Service
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@RequestMapping("/silmarilli")
class SilmarilliController(
    private val silmarilService: SilmarilService,
) {

    @GetMapping
    fun getAll(): List<String> {
        return silmarilService.getAll().map { "${it.name}: ${it.id}" }
    }

    @PostMapping
    // TODO - forged? rename?
    fun forge(@RequestBody dto: SilmarilDto): Unit {
        silmarilService.forge(dto.name)
    }
}

// TODO - name? Or element?
class SilmarilDto(val name: String)

@Service
class SilmarilService(
    private val silmarilRepository: SilmarilRepository,
) {

    fun forge(name: String) {
        silmarilRepository.save(SilmarilEntity(name = name))
    }

    // Returns entity objects instead of DTO here for simplicity
    fun getAll(): List<SilmarilEntity> {
        return silmarilRepository.findAll().toList()
    }
}

interface SilmarilRepository : CrudRepository<SilmarilEntity, Int>

// TODO - add table in liquibase
@Table("silmarilli")
class SilmarilEntity(
    /**
     * [id] is auto generated in the DB.
     * Spring Data JDBC will perform add during [save()] if [id] is [null] or [id] == 0.
     * Otherwise, it will perform [update()].
     */
    @Id
    val id: UUID? = null,

    val name: String,
)