package com.kotlinspring.controller

import com.kotlinspring.restfulapi.RestfulApiApplication
import com.kotlinspring.restfulapi.controller.InstructorController
import com.kotlinspring.restfulapi.dto.InstructorDTO
import com.kotlinspring.restfulapi.service.InstructorService
import com.ninjasquad.springmockk.MockkBean
import io.mockk.every
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.web.reactive.server.WebTestClient

@WebMvcTest(InstructorController::class)
@ContextConfiguration(classes = [RestfulApiApplication::class])
@AutoConfigureWebTestClient
class InstructorControllerUnitTest {
    @Autowired
    lateinit var webTestClient: WebTestClient

    @MockkBean
    lateinit var instructorServiceMock: InstructorService

    @Test
    fun createInstructor() {
        every { instructorServiceMock.createInstructor(any()) } returns InstructorDTO(2, "Anton")

        val result = webTestClient
            .post()
            .uri("/v1/instructors")
            .bodyValue(InstructorDTO(null, "Anton"))
            .exchange()
            .expectStatus().isCreated
            .expectBody(InstructorDTO::class.java)
            .returnResult()
            .responseBody

        Assertions.assertTrue {
            result!!.id == 2
        }
    }

    @Test
    fun createInstructor_validation() {

        every { instructorServiceMock.createInstructor(any()) } returns InstructorDTO(1, "Anton")

        val result = webTestClient
            .post()
            .uri("/v1/instructors")
            .bodyValue(InstructorDTO(null, ""))
            .exchange()
            .expectStatus().isBadRequest
            .expectBody(String::class.java)
            .returnResult()
            .responseBody

        assertEquals("instructorqwe name cannot be blank.", result)
    }
}