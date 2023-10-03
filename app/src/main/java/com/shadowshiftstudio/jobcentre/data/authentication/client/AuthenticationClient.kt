package com.shadowshiftstudio.jobcentre.data.authentication.client

import com.shadowshiftstudio.jobcentre.data.authentication.service.IAuthenticationService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object AuthenticationClient {
    private val retrofit: Retrofit = Retrofit.Builder()
        // Replace with your computer's IP address
        .baseUrl("http://192.168.0.7:8080") // Base URL of the remote server
        .addConverterFactory(GsonConverterFactory.create()) // Converter for JSON processing
        .build()

    val authenticationService: IAuthenticationService = retrofit.create(IAuthenticationService::class.java)
}