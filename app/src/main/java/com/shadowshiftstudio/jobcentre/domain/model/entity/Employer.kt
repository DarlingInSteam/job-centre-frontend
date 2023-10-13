package com.shadowshiftstudio.jobcentre.domain.model.entity

data class Employer (
    val id: Long,
    val employerName: String,
    val address: String,
    val vacancies: List<JobVacancies>,
    val aboutCompany: String,
    val companyPhoto: String
)