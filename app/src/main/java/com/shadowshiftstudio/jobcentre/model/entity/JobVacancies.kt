package com.shadowshiftstudio.jobcentre.model.entity

data class JobVacancies(
    val id: Long,
    val jobType: String,
    val jobTitle: String,
    val salary: Int,
    val archived: Boolean
)
