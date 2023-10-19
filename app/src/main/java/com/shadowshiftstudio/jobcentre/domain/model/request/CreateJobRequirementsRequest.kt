package com.shadowshiftstudio.jobcentre.domain.model.request

import com.shadowshiftstudio.jobcentre.domain.model.enum.EducationLevel

data class CreateJobRequirementsRequest(
    val educationLevel: EducationLevel,
    val ageRange: List<Int>,
    val workExperience: Int
)
