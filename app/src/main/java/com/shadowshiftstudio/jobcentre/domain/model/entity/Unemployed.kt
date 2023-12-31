package com.shadowshiftstudio.jobcentre.domain.model.entity

import com.shadowshiftstudio.jobcentre.domain.model.enum.EducationLevel
import java.util.Date

data class Unemployed (
    val id: Long,
    val passportId: Long,
    val fullName: String,
    val age: Int,
    val educationLevel: EducationLevel,
    val educationalInstitution: String,
    val educationDocumentData: String,
    val speciality: String,
    val workExperience: Int,
    val registrationDate: Date,
    val abilities: List<Ability>,
    val aboutMe: String,
    val unemployedPhoto: String
)