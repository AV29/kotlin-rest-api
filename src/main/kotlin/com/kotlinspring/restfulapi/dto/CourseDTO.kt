package com.kotlinspring.restfulapi.dto

import jakarta.validation.constraints.NotBlank

data class CourseDTO(
    val id: Int?,
    @get:NotBlank(message = "Course name cannot be blank.")
    val name: String,
    @get:NotBlank(message = "Course category cannot be blank.")
    val category: String,
)