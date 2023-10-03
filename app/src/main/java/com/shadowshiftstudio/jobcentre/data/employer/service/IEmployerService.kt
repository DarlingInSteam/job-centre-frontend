package com.shadowshiftstudio.jobcentre.data.employer.service

import com.shadowshiftstudio.jobcentre.model.request.CreateEmployerRequest
import com.shadowshiftstudio.jobcentre.model.request.RegisterRequest
import com.shadowshiftstudio.jobcentre.model.response.AuthenticationResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface IEmployerService {
    @POST("/employer/create")
    fun createEmployer(@Body request: CreateEmployerRequest): Call<String>
}