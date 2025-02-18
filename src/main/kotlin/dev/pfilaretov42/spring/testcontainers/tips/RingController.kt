package dev.pfilaretov42.spring.testcontainers.tips

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Service
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@RequestMapping("/rings")
class RingController(
    private val ringService: RingService,
) {

    @GetMapping
    fun getAll(): List<String> {
        return ringService.getAll().map { "${it.name}: ${it.id}" }
    }

    @PostMapping
    fun forge(@RequestBody dto: RingDto): Unit {
        ringService.forge(dto.name)
    }
}

class RingDto(val name: String)

@Service
class RingService(
    private val ringRepository: RingRepository,
) {

    fun forge(name: String) {
        ringRepository.save(RingEntity(name = name))
    }

    // Returns entity objects instead of DTO here for simplicity
    fun getAll(): List<RingEntity> {
        return ringRepository.findAll().toList()
    }
}

interface RingRepository : CrudRepository<RingEntity, Int>

@Table("rings")
class RingEntity(
    /**
     * [id] is auto generated in the DB.
     * Spring Data JDBC will perform add during [save()] if [id] is [null] or [id] == 0.
     * Otherwise, it will perform [update()].
     */
    @Id
    val id: UUID? = null,

    val name: String,
)