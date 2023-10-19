package com.shadowshiftstudio.jobcentre.domain.employer.repository

import com.shadowshiftstudio.jobcentre.domain.model.entity.JobVacancy
import com.shadowshiftstudio.jobcentre.domain.model.request.CreateJobVacancyRequest

interface IVacancyRepository {
    suspend fun createVacancy(request: CreateJobVacancyRequest): String
    suspend fun getVacancy(jobTitle: String): JobVacancy
    suspend fun applyVacancyUnemployed(vacancyId: Long, username: String): String
    suspend fun archivedVacancy(vacancyId: Long): String
    suspend fun updateVacancy(request: CreateJobVacancyRequest, vacancyId: Long): String
}