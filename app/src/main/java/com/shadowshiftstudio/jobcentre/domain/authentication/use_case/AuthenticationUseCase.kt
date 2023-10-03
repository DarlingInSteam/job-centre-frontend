package com.shadowshiftstudio.jobcentre.domain.authentication.use_case

import com.shadowshiftstudio.jobcentre.domain.authentication.repository.IAuthenticationRepository
import com.shadowshiftstudio.jobcentre.model.request.AuthenticationRequest
import com.shadowshiftstudio.jobcentre.model.request.RegisterRequest
import com.shadowshiftstudio.jobcentre.model.response.AuthenticationResponse

class AuthenticationUseCase(private val authenticationRepository: IAuthenticationRepository) {
    suspend fun registerUser(registerRequest: RegisterRequest): Boolean {
        return authenticationRepository.registerUser(registerRequest)
    }

    suspend fun loginUser(authenticationRequest: AuthenticationRequest): Boolean {
        return authenticationRepository.loginUser(authenticationRequest)
    }

    suspend fun getRefreshToken(): Boolean {
        return authenticationRepository.getRefreshToken()
    }
}