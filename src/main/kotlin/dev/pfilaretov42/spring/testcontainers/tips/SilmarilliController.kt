package dev.pfilaretov42.spring.testcontainers.tips

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
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

@Service
class Fëanor(
    private val treasury: SilmarilTreasury,
) : ElvenSmith<Silmaril> {

    @Transactional
    override fun craftSilmarilli() {
        treasury.save(Silmaril(fate = "Air"))
        treasury.save(Silmaril(fate = "Earth"))
        treasury.save(Silmaril(fate = "Water"))
    }

    // Returns entity objects instead of DTO here for simplicity
    override fun getAllTreasures(): List<Silmaril> = treasury.findAll().toList()
}

interface SilmarilTreasury : CrudRepository<Silmaril, UUID>

@Table("silmarilli")
class Silmaril(
    /**
     * [id] is auto generated in the DB.
     * Spring Data JDBC will perform add during [save()] if [id] is [null] or [id] == 0.
     * Otherwise, it will perform [update()].
     */
    @field:Id
    val id: UUID? = null,

    val fate: String,
)