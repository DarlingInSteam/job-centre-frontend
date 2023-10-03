package com.shadowshiftstudio.jobcentre.data.authentication.service

import com.shadowshiftstudio.jobcentre.model.request.AuthenticationRequest
import com.shadowshiftstudio.jobcentre.model.request.RefreshTokenRequest
import com.shadowshiftstudio.jobcentre.model.request.RegisterRequest
import com.shadowshiftstudio.jobcentre.model.response.AuthenticationResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface IAuthenticationService {
    @POST("/auth/register")
    fun registration(@Body request: RegisterRequest): Call<AuthenticationResponse>

    @POST("/auth/login")
    fun login(@Body request: AuthenticationRequest): Call<AuthenticationResponse>

    @POST("/auth/refresh")
    fun getRefresh(@Body request: RefreshTokenRequest): Call<AuthenticationResponse>
}