package com.shadowshiftstudio.jobcentre.model.entity

import com.shadowshiftstudio.jobcentre.model.enum.Role

data class User(
    val id: Long,
    val username: String,
    val phone: String,
    val role: Role
)
