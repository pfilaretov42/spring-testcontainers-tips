package dev.pfilaretov42.spring.testcontainers.tips

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table
import org.springframework.data.repository.CrudRepository
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

open class ElvenSmith<TREASURE>(
    private val treasury: CrudRepository<TREASURE, UUID>
) {
    open fun forgeTheThreeRings() {
        throw UnsupportedOperationException("I cannot do that")
    }

    open fun craftSilmarilli() {
        throw UnsupportedOperationException("I cannot do that")
    }

    // Returns entity objects instead of DTO here for simplicity
    fun getAllTreasures(): List<TREASURE> {
        return treasury.findAll().toList()
    }
}

@Configuration
class ElvenConfig {
    @Bean
    fun celebrimbor(treasury: RingTreasury): ElvenSmith<Ring> = object : ElvenSmith<Ring>(treasury) {
        @Transactional
        override fun forgeTheThreeRings() {
            treasury.save(Ring(name = "Narya"))
            treasury.save(Ring(name = "Nenya"))
            treasury.save(Ring(name = "Vilya"))
        }
    }

    @Bean
    fun fëanor(treasury: SilmarilTreasury): ElvenSmith<Silmaril> = object : ElvenSmith<Silmaril>(treasury) {
        @Transactional
        override fun craftSilmarilli() {
            treasury.save(Silmaril(fate = "Air"))
            treasury.save(Silmaril(fate = "Earth"))
            treasury.save(Silmaril(fate = "Water"))
        }
    }
}

interface RingTreasury : CrudRepository<Ring, UUID>

@Table("rings")
class Ring(
    /**
     * [id] is auto generated in the DB.
     * Spring Data JDBC will perform add during [save()] if [id] is [null] or [id] == 0.
     * Otherwise, it will perform [update()].
     */
    @Id
    val id: UUID? = null,

    val name: String,
)