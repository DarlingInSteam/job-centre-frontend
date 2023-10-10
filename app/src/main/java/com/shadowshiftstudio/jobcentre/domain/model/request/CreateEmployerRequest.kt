package com.shadowshiftstudio.jobcentre.domain.model.request

data class CreateEmployerRequest (
    val employerName: String,
    val address: String,
    val username: String
)