package com.shadowshiftstudio.jobcentre.domain.authentication.repository

import com.shadowshiftstudio.jobcentre.domain.model.request.AuthenticationRequest
import com.shadowshiftstudio.jobcentre.domain.model.request.RegisterRequest
import com.shadowshiftstudio.jobcentre.domain.model.response.AuthenticationResponse

interface IAuthenticationRepository {
    suspend fun registerUser(registerRequest: RegisterRequest): Boolean
    suspend fun loginUser(authenticationRequest: AuthenticationRequest): Boolean
    suspend fun getRefreshToken(): Boolean
}