package com.shadowshiftstudio.jobcentre.domain.model.request

import com.shadowshiftstudio.jobcentre.domain.model.enum.Role

data class RegisterRequest (
    val username: String,
    val phone: String,
    val password: String,
    val role: Role
)