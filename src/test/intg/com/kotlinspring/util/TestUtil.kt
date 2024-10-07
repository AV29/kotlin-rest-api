package com.kotlinspring.util

import com.kotlinspring.restfulapi.dto.CourseDTO
import com.kotlinspring.restfulapi.entity.Course
import com.kotlinspring.restfulapi.entity.Instructor

fun courseEntityList(instructor: Instructor? = null) = listOf(
    Course(null,
        "Build RestFul APis using SpringBoot and Kotlin", "Development", instructor),
    Course(null,
        "Build Reactive Microservices using Spring WebFlux/SpringBoot", "Development"
        , instructor
    ),
    Course(null,
        "Wiremock for Java Developers", "Development" , instructor
    )
)

fun instructorEntity(name: String = "Anton") = Instructor(null, name)

fun courseDTO(
    id: Int? = null,
    name: String = "Build RestFul APis using Spring Boot and Kotlin",
    category: String = "Anton V",
    instructorId: Int? = 1
) = CourseDTO(
    id,
    name,
    category,
    instructorId
)
