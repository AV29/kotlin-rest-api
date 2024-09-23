package com.kotlinspring.controller

import com.kotlinspring.restfulapi.RestfulApiApplication
import com.kotlinspring.restfulapi.controller.GreetingController
import com.kotlinspring.restfulapi.service.GreetingsService
import com.ninjasquad.springmockk.MockkBean
import io.mockk.every
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.web.reactive.server.WebTestClient

@SpringBootTest(classes = [RestfulApiApplication::class, GreetingController::class], webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@AutoConfigureWebTestClient
class GreetingControllerUnitTest {
    @Autowired
    lateinit var webTestClient: WebTestClient

    @MockkBean
    lateinit var greetingsServiceMock: GreetingsService

    @Test
    fun retrieveGreeting() {
        val name = "Anton"

        every { greetingsServiceMock.retrieveGreeting(any()) } returns "Anton, Hello from default profile!!"

        val result = webTestClient
            .get()
            .uri("/v1/greetings/{name}", name)
            .exchange()
            .expectStatus().is2xxSuccessful
            .expectBody(String::class.java)
            .returnResult()

        Assertions.assertEquals("Anton, Hello from default profile!!", result.responseBody)
    }
}