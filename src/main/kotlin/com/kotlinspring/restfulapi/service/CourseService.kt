package com.kotlinspring.restfulapi.service

import com.kotlinspring.restfulapi.dto.CourseDTO
import com.kotlinspring.restfulapi.entity.Course
import com.kotlinspring.restfulapi.repository.CourseRepository
import mu.KLogging
import org.springframework.stereotype.Service

@Service
class CourseService(val courseRepository: CourseRepository) {
    companion object: KLogging()

    fun addCourse(courseDTO: CourseDTO): CourseDTO {

        val courseEntity = courseDTO.let {
            Course(null, it.name, it.category)
        }

        val savedCourse = courseRepository.save(courseEntity)

        logger.info("Saved course 1 is: $savedCourse")

        return savedCourse.let {
            CourseDTO(it.id, it.name, it.category)
        }
    }

    fun retrieveAllCourses(): List<CourseDTO> {
        return courseRepository.findAll().map {
            CourseDTO(it.id, it.name, it.category)
        }
    }
}