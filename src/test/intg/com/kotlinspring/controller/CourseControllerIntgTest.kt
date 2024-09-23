package com.kotlinspring.controller

import com.kotlinspring.restfulapi.RestfulApiApplication
import com.kotlinspring.restfulapi.controller.CourseController
import com.kotlinspring.restfulapi.dto.CourseDTO
import com.kotlinspring.restfulapi.entity.Course
import com.kotlinspring.restfulapi.repository.CourseRepository
import com.kotlinspring.util.courseEntityList
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.web.reactive.server.WebTestClient

@SpringBootTest(classes = [RestfulApiApplication::class, CourseController::class], webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@AutoConfigureWebTestClient
class CourseControllerIntgTest {
    @Autowired
    lateinit var webTestClient: WebTestClient

    @Autowired
    lateinit var courseRepository: CourseRepository

    @BeforeEach
    fun setup() {
        courseRepository.deleteAll()
        val courses = courseEntityList()
        courseRepository.saveAll(courses)
    }

    @Test
    fun addCourse() {
        val courseDTO = CourseDTO(null, "name1", "category1")

        val result = webTestClient
            .post()
            .uri("/v1/courses")
            .bodyValue(courseDTO)
            .exchange()
            .expectStatus().isCreated
            .expectBody(CourseDTO::class.java)
            .returnResult()
            .responseBody

        Assertions.assertTrue {
            result!!.id != null
        }
    }

    @Test
    fun retrieveAllCourses() {
        val result = webTestClient
            .get()
            .uri("/v1/courses")
            .exchange()
            .expectStatus().isOk
            .expectBodyList(CourseDTO::class.java)
            .returnResult()
            .responseBody

        assertEquals(3, result!!.size)
    }

    @Test
    fun updateCourse() {

        val course =  Course(null,
            "Build RestFul APis using SpringBoot and Kotlin", "Development")

        val savedCourse = courseRepository.save(course)

        val courseDTO = CourseDTO(null, "name1", "category1");

        val result = webTestClient
            .put()
            .uri("/v1/courses/{courseId}", savedCourse.id)
            .bodyValue(courseDTO)
            .exchange()
            .expectStatus().isOk
            .expectBody(CourseDTO::class.java)
            .returnResult()
            .responseBody

        assertEquals("name1", result!!.name)
    }

    @Test
    fun deleteCourse() {

        val course =  Course(null,
            "Build RestFul APis using SpringBoot and Kotlin", "Development")

        val savedCourse = courseRepository.save(course)

        val result = webTestClient
            .delete()
            .uri("/v1/courses/{courseId}", savedCourse.id)
            .exchange()
            .expectStatus().isNoContent
    }
}