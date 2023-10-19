package com.shadowshiftstudio.jobcentre.domain.model.request

data class CreateJobVacancyRequest(
    val jobType: String,
    val jobTitle: String,
    val salary: Int,
    val username: String,
    val createJobRequirementsRequest: CreateJobRequirementsRequest
)
