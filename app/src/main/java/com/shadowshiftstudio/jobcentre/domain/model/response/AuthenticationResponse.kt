package com.shadowshiftstudio.jobcentre.domain.model.response

import com.shadowshiftstudio.jobcentre.domain.model.enum.Role

data class AuthenticationResponse(
    val accessToken: String,
    val token: String,
    val username: String,
    val role: Role
)
