package com.shadowshiftstudio.jobcentre.data.employer.api_request

import com.shadowshiftstudio.jobcentre.domain.employer.repository.IVacancyRepository
import com.shadowshiftstudio.jobcentre.domain.model.entity.JobVacancy
import com.shadowshiftstudio.jobcentre.domain.model.request.CreateJobVacancyRequest

class VacancyRequest: IVacancyRepository {
    override suspend fun createVacancy(request: CreateJobVacancyRequest): String {
        TODO("Not yet implemented")
    }

    override suspend fun getVacancy(jobTitle: String): JobVacancy {
        TODO("Not yet implemented")
    }

    override suspend fun applyVacancyUnemployed(vacancyId: Long, username: String): String {
        TODO("Not yet implemented")
    }

    override suspend fun archivedVacancy(vacancyId: Long): String {
        TODO("Not yet implemented")
    }

    override suspend fun updateVacancy(request: CreateJobVacancyRequest, vacancyId: Long): String {
        TODO("Not yet implemented")
    }
}