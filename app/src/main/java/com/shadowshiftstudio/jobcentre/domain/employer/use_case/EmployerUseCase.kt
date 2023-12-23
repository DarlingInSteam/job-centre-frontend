package com.shadowshiftstudio.jobcentre.domain.employer.use_case

import com.shadowshiftstudio.jobcentre.domain.employer.repository.IEmployerRepository
import com.shadowshiftstudio.jobcentre.domain.model.entity.Employer
import com.shadowshiftstudio.jobcentre.domain.model.request.AcceptUnemployedRequest
import com.shadowshiftstudio.jobcentre.domain.model.request.CreateEmployerRequest
import com.shadowshiftstudio.jobcentre.domain.model.response.GetAppliesForVacancies
import com.shadowshiftstudio.jobcentre.domain.model.response.GetJobVacancyResponse

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

    suspend fun addNewName(newName: String, id: Long): String {
        return repository.addNewName(newName, id)
    }

    suspend fun addNewAddress(newAddress: String, id: Long): String {
        return repository.addNewAddress(newAddress, id)
    }

    suspend fun addPhoto(photo: String, id: Long): String {
        return repository.addPhoto(photo, id)
    }

    suspend fun getJobVacancies(username: String): List<GetJobVacancyResponse> {
        return repository.getJobVacancies(username)
    }

    suspend fun getAppliesVacancy(username: String): List<GetAppliesForVacancies> {
        return repository.getAppliesVacancy(username)
    }

    suspend fun acceptUnemployed(request: AcceptUnemployedRequest): String {
        return repository.acceptUnemployed(request)
    }

    suspend fun rejectUnemployed(request: AcceptUnemployedRequest): String {
        return repository.rejectUnemployed(request)
    }
}