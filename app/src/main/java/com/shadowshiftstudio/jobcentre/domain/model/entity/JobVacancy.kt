package com.shadowshiftstudio.jobcentre.domain.model.entity

data class JobVacancy(
    val id: Long,
    val jobType: String,
    val jobTitle: String,
    val salary: Int,
    val archived: Boolean
)
