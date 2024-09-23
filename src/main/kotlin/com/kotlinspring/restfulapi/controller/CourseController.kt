package com.kotlinspring.restfulapi.controller

import com.kotlinspring.restfulapi.dto.CourseDTO
import com.kotlinspring.restfulapi.service.CourseService
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/v1/courses")
class CourseController(val courseService: CourseService) {

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun addCourse(@RequestBody courseDTO: CourseDTO): CourseDTO {
        return courseService.addCourse(courseDTO)
    }

    @GetMapping
    fun retrieveAllCourses(): List<CourseDTO> {
        return courseService.retrieveAllCourses()
    }

    @PutMapping("/{course_id}")
    fun updateCourse(@PathVariable("course_id") courseId: Int, @RequestBody courseDTO: CourseDTO) = courseService.updateCourse(courseId, courseDTO)
}