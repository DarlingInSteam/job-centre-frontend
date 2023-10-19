package com.shadowshiftstudio.jobcentre.domain.employer.use_case

import com.shadowshiftstudio.jobcentre.domain.employer.repository.IEmployerRepository
import com.shadowshiftstudio.jobcentre.domain.model.entity.Employer
import com.shadowshiftstudio.jobcentre.domain.model.request.CreateEmployerRequest

class EmployerUseCase(private val repository: IEmployerRepository) {
    suspend fun createEmployer(request: CreateEmployerRequest): Boolean {
        return repository.createEmployer(request)
    }

    suspend fun getEmployerByUsername(username: String): Employer {
        return repository.getEmployerByUsername(username)
    }

    suspend fun addAboutCompany(aboutCompany: String, id: Long): String {
        return repository.addAboutCompany(aboutCompany, id)
    }
}