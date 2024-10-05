package com.kotlinspring.controller

import com.kotlinspring.restfulapi.RestfulApiApplication
import com.kotlinspring.restfulapi.controller.CourseController
import com.kotlinspring.restfulapi.dto.CourseDTO
import com.kotlinspring.restfulapi.entity.Course
import com.kotlinspring.restfulapi.service.CourseService
import com.kotlinspring.util.courseDTO
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest

import com.ninjasquad.springmockk.MockkBean
import io.mockk.every
import io.mockk.just
import io.mockk.runs
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.web.reactive.server.WebTestClient

@WebMvcTest(CourseController::class)
@ContextConfiguration(classes = [RestfulApiApplication::class])
@AutoConfigureWebTestClient
class CourseControllerUnitTest {
    @Autowired
    lateinit var webTestClient: WebTestClient

    @MockkBean
    lateinit var courseServiceMock: CourseService

    @Test
    fun addCourse() {
        every { courseServiceMock.addCourse(any()) } returns courseDTO(id = 1)

        val result = webTestClient
            .post()
            .uri("/v1/courses")
            .bodyValue(courseDTO())
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
    fun addCourse_validation() {
        every { courseServiceMock.addCourse(any()) } returns courseDTO(id = 1)

        val result = webTestClient
            .post()
            .uri("/v1/courses")
            .bodyValue(courseDTO(null, "", ""))
            .exchange()
            .expectStatus().isBadRequest
            .expectBody(String::class.java)
            .returnResult()
            .responseBody

        assertEquals("Course category cannot be blank.,Course name cannot be blank.", result)
    }

    @Test
    fun addCourse_general_exception() {
        val errorMessage = "error!";
        every { courseServiceMock.addCourse(any()) } throws RuntimeException(errorMessage)

        val result = webTestClient
            .post()
            .uri("/v1/courses")
            .bodyValue(courseDTO(null, "adsasd", "asdasd"))
            .exchange()
            .expectStatus().is5xxServerError
            .expectBody(String::class.java)
            .returnResult()
            .responseBody

        assertEquals(errorMessage, result)
    }

    @Test
    fun retrieveAllCourses() {
        every { courseServiceMock.retrieveAllCourses(any()) }.returns(listOf(courseDTO(id = 1), courseDTO(id = 2)))

        val result = webTestClient
            .get()
            .uri("/v1/courses")
            .exchange()
            .expectStatus().isOk
            .expectBodyList(CourseDTO::class.java)
            .returnResult()
            .responseBody

        assertEquals(2, result!!.size)
    }

    @Test
    fun updateCourse() {
        every { courseServiceMock.updateCourse(any(), any()) } returns courseDTO(id = 100, name = "test test")

        val result = webTestClient
            .put()
            .uri("/v1/courses/{courseId}", 100)
            .bodyValue(CourseDTO(null, "name1", "category1"))
            .exchange()
            .expectStatus().isOk
            .expectBody(CourseDTO::class.java)
            .returnResult()
            .responseBody

        assertEquals("test test", result!!.name)
    }

    @Test
    fun deleteCourse() {

        every { courseServiceMock.deleteCourse(any()) } just runs

        webTestClient
            .delete()
            .uri("/v1/courses/{courseId}", 100)
            .exchange()
            .expectStatus().isNoContent
    }
}