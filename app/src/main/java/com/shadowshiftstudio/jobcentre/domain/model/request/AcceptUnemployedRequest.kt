package com.shadowshiftstudio.jobcentre.domain.model.request

data class AcceptUnemployedRequest(
    val vacancyId: Long,
    val usernameEmployed: String,
    val unemployedId: Long
)
