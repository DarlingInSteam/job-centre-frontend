package com.shadowshiftstudio.jobcentre.domain.employer.use_case

import com.shadowshiftstudio.jobcentre.domain.employer.repository.IVacancyRepository
import com.shadowshiftstudio.jobcentre.domain.model.entity.JobVacancy
import com.shadowshiftstudio.jobcentre.domain.model.request.CreateJobVacancyRequest

class VacancyUseCase(private val repository: IVacancyRepository) {
    suspend fun createVacancy(request: CreateJobVacancyRequest): String {
        return repository.createVacancy(request)
    }

    suspend fun getVacancy(job_id: Long): JobVacancy? {
        return repository.getVacancy(job_id)
    }

    suspend fun applyVacancyUnemployed(vacancyId: Long, username: String): String {
        return repository.applyVacancyUnemployed(vacancyId, username)
    }

    suspend fun archivedVacancy(vacancyId: Long): String {
        return repository.archivedVacancy(vacancyId)
    }

    suspend fun updateVacancy(request: CreateJobVacancyRequest, vacancyId: Long): String {
        return repository.updateVacancy(request, vacancyId)
    }
}