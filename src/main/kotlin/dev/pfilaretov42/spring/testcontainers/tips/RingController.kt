package dev.pfilaretov42.spring.testcontainers.tips

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@RequestMapping("/rings")
class RingController(
    private val celebrimbor: ElvenSmith<Ring>,
) {

    @PostMapping
    fun forge(): Unit {
        celebrimbor.forgeTheThreeRings()
    }

    @GetMapping
    fun getAll(): List<String> {
        return celebrimbor.getAllTreasures().map { it.name }
    }
}

interface ElvenSmith<TREASURE> {
    fun forgeTheThreeRings() {
        throw UnsupportedOperationException("I cannot do that")
    }

    fun craftSilmarilli() {
        throw UnsupportedOperationException("I cannot do that")
    }

    fun getAllTreasures(): List<TREASURE>
}

@Service
class Celebrimbor(
    private val treasury: RingTreasury,
) : ElvenSmith<Ring> {

    @Transactional
    override fun forgeTheThreeRings() {
        treasury.save(Ring(name = "Narya"))
        treasury.save(Ring(name = "Nenya"))
        treasury.save(Ring(name = "Vilya"))
    }

    // Returns entity objects instead of DTO here for simplicity
    override fun getAllTreasures(): List<Ring> = treasury.findAll().toList()
}

interface RingTreasury : CrudRepository<Ring, UUID>

@Table("rings")
class Ring(
    /**
     * [id] is auto generated in the DB.
     * Spring Data JDBC will perform add during [save()] if [id] is [null] or [id] == 0.
     * Otherwise, it will perform [update()].
     */
    @field:Id
    val id: UUID? = null,

    val name: String,
)