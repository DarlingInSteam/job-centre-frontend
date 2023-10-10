package com.shadowshiftstudio.jobcentre.domain.model.request

data class AuthenticationRequest(
    val username: String,
    val phone: String,
    val password: String
)
