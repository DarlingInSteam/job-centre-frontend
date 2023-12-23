package com.shadowshiftstudio.jobcentre.domain.model.response

import com.shadowshiftstudio.jobcentre.domain.model.entity.JobRequirements
import com.shadowshiftstudio.jobcentre.domain.model.entity.JobVacancy
import com.shadowshiftstudio.jobcentre.domain.model.entity.Unemployed

data class GetAppliesForVacancies(
    val jobVacanciesDto: JobVacancy,
    val jobRequirementsDto: JobRequirements,
    val unemployedDto: List<Unemployed>
)
