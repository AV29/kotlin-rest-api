package com.kotlinspring.controller

import com.kotlinspring.restfulapi.RestfulApiApplication
import com.kotlinspring.restfulapi.controller.InstructorController
import com.kotlinspring.restfulapi.dto.InstructorDTO
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.web.reactive.server.WebTestClient

@SpringBootTest(classes = [RestfulApiApplication::class, InstructorController::class], webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@AutoConfigureWebTestClient
class InstructorControllerIntgTest {
    @Autowired
    lateinit var webTestClient: WebTestClient

    @BeforeEach
    fun setup() {
    }

    @Test
    fun createInstructor() {
        val instructorDTO = InstructorDTO(null, "Anton")

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
            result!!.id != null
        }
    }
}