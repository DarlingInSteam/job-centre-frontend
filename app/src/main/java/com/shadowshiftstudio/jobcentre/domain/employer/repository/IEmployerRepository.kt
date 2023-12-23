package com.shadowshiftstudio.jobcentre.domain.employer.repository

import com.shadowshiftstudio.jobcentre.domain.model.entity.Employer
import com.shadowshiftstudio.jobcentre.domain.model.request.AcceptUnemployedRequest
import com.shadowshiftstudio.jobcentre.domain.model.request.CreateEmployerRequest
import com.shadowshiftstudio.jobcentre.domain.model.response.GetAppliesForVacancies
import com.shadowshiftstudio.jobcentre.domain.model.response.GetJobVacancyResponse

interface IEmployerRepository {
    suspend fun createEmployer(request: CreateEmployerRequest): Boolean
    suspend fun getEmployerByUsername(username: String): Employer
    suspend fun addAboutCompany(aboutCompany: String, id: Long): String
    suspend fun addNewName(newName: String, id: Long): String
    suspend fun addNewAddress(newAddress: String, id: Long): String
    suspend fun addPhoto(photo: String, id: Long): String
    suspend fun getJobVacancies(username: String): List<GetJobVacancyResponse>
    suspend fun getAppliesVacancy(username: String): List<GetAppliesForVacancies>
    suspend fun acceptUnemployed(request: AcceptUnemployedRequest): String
    suspend fun rejectUnemployed(request: AcceptUnemployedRequest): String
}