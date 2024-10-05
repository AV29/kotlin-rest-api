package com.kotlinspring.restfulapi.entity

import jakarta.persistence.*

@Entity
@Table(name = "Instructors")
data class Instructor(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    val id: Int?,
    var name: String,
    @OneToMany(
        cascade = [CascadeType.ALL],
        mappedBy = "instructor",
        orphanRemoval = true
    )
    var courses: List<Course> = mutableListOf()
)