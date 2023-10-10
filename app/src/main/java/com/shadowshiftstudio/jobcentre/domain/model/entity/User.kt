package com.shadowshiftstudio.jobcentre.domain.model.entity

import com.shadowshiftstudio.jobcentre.domain.model.enum.Role

data class User(
    val id: Long,
    val username: String,
    val phone: String,
    val role: Role
)
