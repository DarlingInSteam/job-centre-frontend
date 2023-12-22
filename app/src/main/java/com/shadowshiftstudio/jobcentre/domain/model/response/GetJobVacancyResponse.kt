package com.shadowshiftstudio.jobcentre.domain.model.response

import com.shadowshiftstudio.jobcentre.domain.model.entity.JobRequirements
import com.shadowshiftstudio.jobcentre.domain.model.entity.JobVacancy

data class GetJobVacancyResponse (
    val jobVacancy: JobVacancy,
    val jobRequirement: JobRequirements
)