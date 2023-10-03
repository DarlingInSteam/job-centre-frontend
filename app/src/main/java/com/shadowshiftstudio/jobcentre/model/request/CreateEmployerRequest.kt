package com.shadowshiftstudio.jobcentre.model.request

data class CreateEmployerRequest (
    val employerName: String,
    val address: String,
    val username: String
)