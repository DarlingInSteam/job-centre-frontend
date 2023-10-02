package com.shadowshiftstudio.jobcentre.model.entity

data class Employer (
    val id: Long,
    val employerName: String,
    val address: String,
    val vacancies: List<JobVacancies>
)