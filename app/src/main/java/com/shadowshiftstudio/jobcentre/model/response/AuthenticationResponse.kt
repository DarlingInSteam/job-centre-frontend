package com.shadowshiftstudio.jobcentre.model.response

import com.shadowshiftstudio.jobcentre.model.enum.Role

data class AuthenticationResponse(
    val accessToken: String,
    val token: String,
    val username: String,
    val role: Role
)
