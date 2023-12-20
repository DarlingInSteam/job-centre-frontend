package com.shadowshiftstudio.jobcentre.domain.model.entity

import com.shadowshiftstudio.jobcentre.domain.model.enum.EducationLevel

data class JobRequirements(
    val id: Long,
    val educationLevel: EducationLevel,
    val ageRangeUpper: Int,
    val ageRangeLower: Int,
    val workExperience: Int
)
