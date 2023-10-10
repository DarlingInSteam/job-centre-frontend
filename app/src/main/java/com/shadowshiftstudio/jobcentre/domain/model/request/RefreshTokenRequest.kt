package com.shadowshiftstudio.jobcentre.domain.model.request

data class RefreshTokenRequest(
    val token: String,
    val username: String
)
