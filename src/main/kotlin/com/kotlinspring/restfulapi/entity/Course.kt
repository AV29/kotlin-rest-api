package com.kotlinspring.restfulapi.entity

import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table


@Entity
@Table(name = "Courses")
data class Course(
    @Id val id: Int?,
    val name: String,
    val category: String,
)
