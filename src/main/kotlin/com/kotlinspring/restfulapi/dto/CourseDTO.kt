package com.kotlinspring.restfulapi.dto

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull

data class CourseDTO(
    val id: Int?,
    @get:NotBlank(message = "Course name cannot be blank.")
    val name: String,
    @get:NotBlank(message = "Course category cannot be blank.")
    val category: String,
    @get:NotNull(message = "instructorId must not be null.")
    val instructorId: Int? = null,
)