package com.kotlinspring.restfulapi.repository

import com.kotlinspring.restfulapi.entity.Instructor
import org.springframework.data.repository.CrudRepository

interface InstructorRepository: CrudRepository<Instructor, Int> {}