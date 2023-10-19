package com.shadowshiftstudio.jobcentre.domain.employer.repository

import com.shadowshiftstudio.jobcentre.domain.model.entity.Employer
import com.shadowshiftstudio.jobcentre.domain.model.request.CreateEmployerRequest

interface IEmployerRepository {
    suspend fun createEmployer(request: CreateEmployerRequest): Boolean
    suspend fun getEmployerByUsername(username: String): Employer
    suspend fun addAboutCompany(aboutCompany: String, id: Long): String
}