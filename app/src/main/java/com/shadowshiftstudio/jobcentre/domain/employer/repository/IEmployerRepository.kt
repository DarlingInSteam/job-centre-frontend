package com.shadowshiftstudio.jobcentre.domain.employer.repository

import com.shadowshiftstudio.jobcentre.model.entity.Employer
import com.shadowshiftstudio.jobcentre.model.request.CreateEmployerRequest

interface IEmployerRepository {
    suspend fun createEmployer(request: CreateEmployerRequest): Boolean
    suspend fun getEmployerByUsername(username: String): Employer
}