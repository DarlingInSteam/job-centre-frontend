package com.shadowshiftstudio.jobcentre.data.employer.service

import com.shadowshiftstudio.jobcentre.domain.model.entity.Employer
import com.shadowshiftstudio.jobcentre.domain.model.request.CreateEmployerRequest
import com.shadowshiftstudio.jobcentre.domain.model.request.RegisterRequest
import com.shadowshiftstudio.jobcentre.domain.model.response.AuthenticationResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface IEmployerService {
    @POST("/employer/create")
    fun createEmployer(@Body request: CreateEmployerRequest): Call<String>

    @GET("/employer/get_employer")
    fun getEmployerByUsername(@Query("username") username: String): Call<Employer>

    @POST("/employer/add_about_company")
    fun addAboutCompany(@Query("aboutCompany") aboutCompany: String, @Query("id") id: Long): Call<String>
}