package com.shadowshiftstudio.jobcentre.domain.unemployed.repository

import com.shadowshiftstudio.jobcentre.domain.model.entity.Unemployed

interface IUnemployedRepository {
    suspend fun getUnemployedByUsername(username: String): Unemployed
    suspend fun getAllUnemployed(): List<Unemployed>
}