package com.shadowshiftstudio.jobcentre.model.request

data class RefreshTokenRequest(
    val token: String,
    val username: String
)
