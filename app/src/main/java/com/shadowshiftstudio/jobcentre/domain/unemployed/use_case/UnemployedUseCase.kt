package com.shadowshiftstudio.jobcentre.domain.unemployed.use_case

import com.shadowshiftstudio.jobcentre.domain.unemployed.repository.IUnemployedRepository
import com.shadowshiftstudio.jobcentre.domain.model.entity.Unemployed

class UnemployedUseCase(private val unemployedRepository: IUnemployedRepository) {
    suspend fun getUnemployedByUsername(username: String): Unemployed {
        return unemployedRepository.getUnemployedByUsername(username)
    }
    suspend fun getAllUnemployed(): List<Unemployed> {
        return unemployedRepository.getAllUnemployed()
    }
}