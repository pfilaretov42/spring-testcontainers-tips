package dev.pfilaretov42.spring.testcontainers.tips

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table
import org.springframework.data.repository.CrudRepository
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@RequestMapping("/silmarilli")
class SilmarilliController(
    private val fëanor: ElvenSmith<Silmaril>,
) {

    @PostMapping
    fun craft(): Unit {
        fëanor.craftSilmarilli()
    }

    @GetMapping
    fun getAll(): List<String> {
        return fëanor.getAllTreasures().map { it.fate }
    }
}

interface SilmarilTreasury : CrudRepository<Silmaril, UUID>

@Table("silmarilli")
class Silmaril(
    /**
     * [id] is auto generated in the DB.
     * Spring Data JDBC will perform add during [save()] if [id] is [null] or [id] == 0.
     * Otherwise, it will perform [update()].
     */
    @Id val id: UUID? = null,

    val fate: String,
)