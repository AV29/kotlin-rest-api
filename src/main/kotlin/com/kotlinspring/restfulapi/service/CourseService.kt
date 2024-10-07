package com.kotlinspring.restfulapi.service

import com.kotlinspring.restfulapi.dto.CourseDTO
import com.kotlinspring.restfulapi.entity.Course
import com.kotlinspring.restfulapi.exception.CourseNotFoundException
import com.kotlinspring.restfulapi.exception.InstructorNotValidException
import com.kotlinspring.restfulapi.repository.CourseRepository
import mu.KLogging
import org.springframework.stereotype.Service

@Service
class CourseService(val courseRepository: CourseRepository, val instructorService: InstructorService) {
    companion object: KLogging()

    fun addCourse(courseDTO: CourseDTO): CourseDTO {

        val foundInstructor = instructorService.findByInstructorId(courseDTO.instructorId!!)

        if(!foundInstructor.isPresent) {
            throw InstructorNotValidException("Instructor Not Valid for the Id: ${courseDTO.instructorId}");
        }

        val courseEntity = courseDTO.let {
            Course(null, it.name, it.category, foundInstructor.get())
        }

        val savedCourse = courseRepository.save(courseEntity)

        logger.info("Saved course 1 is: $savedCourse")

        return savedCourse.let {
            CourseDTO(it.id, it.name, it.category, it.instructor!!.id)
        }
    }

    fun retrieveAllCourses(courseName: String?): List<CourseDTO> {
        val courses = courseName?.let {
            courseRepository.findCoursesByName(courseName)
        } ?: courseRepository.findAll()

        return courses.map {
            CourseDTO(it.id, it.name, it.category)
        }
    }

    fun updateCourse(courseId: Int, courseDTO: CourseDTO): CourseDTO {
        val existingCourse = courseRepository.findById(courseId)

        return if(existingCourse.isPresent) {
            existingCourse.get()
                .let {
                    it.name = courseDTO.name
                    it.category = courseDTO.category
                    val savedCourse = courseRepository.save(it)
                    CourseDTO(savedCourse.id, savedCourse.name, savedCourse.category)
                }
        } else {
            throw CourseNotFoundException("No course found for the passed Id: $courseId")
        }
    }

    fun deleteCourse(courseId: Int): Unit {
        val existingCourse = courseRepository.findById(courseId)

        if(existingCourse.isPresent) {
            courseRepository.deleteById(courseId)
        } else {
            throw CourseNotFoundException("No course found for the passed Id: $courseId")
        }
    }
}