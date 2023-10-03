package com.shadowshiftstudio.jobcentre.model.request

import com.shadowshiftstudio.jobcentre.model.enum.Role

data class RegisterRequest (
    val username: String,
    val phone: String,
    val password: String,
    val role: Role
)