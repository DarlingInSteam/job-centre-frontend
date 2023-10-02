package com.shadowshiftstudio.jobcentre.model.entity

import com.shadowshiftstudio.jobcentre.model.enum.EducationLevel

data class JobRequirements(
    val id: Long,
    val educationLevel: EducationLevel,
    val ageRange: List<Int>,
    val workExperience: Int
)
