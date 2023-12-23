package com.shadowshiftstudio.jobcentre.domain.employer.repository

import com.shadowshiftstudio.jobcentre.domain.model.entity.JobVacancy
import com.shadowshiftstudio.jobcentre.domain.model.request.CreateJobVacancyRequest
import com.shadowshiftstudio.jobcentre.domain.model.response.GetJobVacancyResponse

interface IVacancyRepository {
    suspend fun createVacancy(request: CreateJobVacancyRequest): String
    suspend fun getVacancy(job_id: Long): JobVacancy?
    suspend fun applyVacancyUnemployed(vacancyId: Long, username: String): String
    suspend fun archivedVacancy(vacancyId: Long): String
    suspend fun updateVacancy(request: CreateJobVacancyRequest, vacancyId: Long): String
    suspend fun getAllVacancy(): List<GetJobVacancyResponse>
}