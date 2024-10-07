package com.kotlinspring.controller

import com.kotlinspring.restfulapi.RestfulApiApplication
import com.kotlinspring.restfulapi.controller.InstructorController
import com.kotlinspring.restfulapi.dto.InstructorDTO
import com.kotlinspring.restfulapi.service.InstructorService
import com.ninjasquad.springmockk.MockkBean
import io.mockk.every
import org.junit.jupiter.api.Assertions
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
    fun addCourse() {
        val instructorDTO = InstructorDTO(2, "Anton")
        every { instructorServiceMock.createInstructor(any()) } returns instructorDTO

        val result = webTestClient
            .post()
            .uri("/v1/instructors")
            .bodyValue(instructorDTO)
            .exchange()
            .expectStatus().isCreated
            .expectBody(InstructorDTO::class.java)
            .returnResult()
            .responseBody

        Assertions.assertTrue {
            result!!.id == 2
        }
    }
}