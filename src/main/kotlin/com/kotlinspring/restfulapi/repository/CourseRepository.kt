package com.kotlinspring.restfulapi.repository

import com.kotlinspring.restfulapi.entity.Course
import org.springframework.data.repository.CrudRepository

interface CourseRepository: CrudRepository<Course, Int>