package com.shadowshiftstudio.jobcentre.data.authentication.client

import com.shadowshiftstudio.jobcentre.data.authentication.service.IAuthenticationService
import com.shadowshiftstudio.jobcentre.data.employer.service.IEmployerService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object AuthenticationClient {
    private val retrofit: Retrofit = Retrofit.Builder()
        // Replace with your computer's IP address
        .baseUrl("http://192.168.86.167:8080") // Base URL of the remote server
        .addConverterFactory(GsonConverterFactory.create()) // Converter for JSON processing
        .build()

    val authenticationService: IAuthenticationService = retrofit.create(IAuthenticationService::class.java)
    val employerService: IEmployerService = retrofit.create(IEmployerService::class.java)
}