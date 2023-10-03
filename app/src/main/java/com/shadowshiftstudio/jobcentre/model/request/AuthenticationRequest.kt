package com.shadowshiftstudio.jobcentre.model.request

data class AuthenticationRequest(
    val username: String,
    val phone: String,
    val password: String
)
