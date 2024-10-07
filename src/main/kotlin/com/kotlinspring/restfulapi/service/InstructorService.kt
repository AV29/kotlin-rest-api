package com.kotlinspring.restfulapi.service

import com.kotlinspring.restfulapi.dto.InstructorDTO
import com.kotlinspring.restfulapi.entity.Instructor
import com.kotlinspring.restfulapi.repository.InstructorRepository
import mu.KLogging
import org.springframework.stereotype.Service
import java.util.Optional

@Service
class InstructorService(val instructorRepository: InstructorRepository) {
    companion object: KLogging()

    fun createInstructor(instructorDTO: InstructorDTO): InstructorDTO {

        val instructorEntity = instructorDTO.let {
            Instructor(it.id, it.name)
        }

        val createdInstructor = instructorRepository.save(instructorEntity)

        logger.info("Saved instructor 1 is: $createdInstructor")

        return createdInstructor.let {
            InstructorDTO(it.id, it.name)
        }
    }

    fun findByInstructorId(instructorId: Int): Optional<Instructor> {
        return instructorRepository.findById(instructorId);
    }
}