package com.shadowshiftstudio.jobcentre.data.employer.service

import com.shadowshiftstudio.jobcentre.domain.model.entity.Employer
import com.shadowshiftstudio.jobcentre.domain.model.request.AcceptUnemployedRequest
import com.shadowshiftstudio.jobcentre.domain.model.request.CreateEmployerRequest
import com.shadowshiftstudio.jobcentre.domain.model.request.RegisterRequest
import com.shadowshiftstudio.jobcentre.domain.model.response.AuthenticationResponse
import com.shadowshiftstudio.jobcentre.domain.model.response.GetAppliesForVacancies
import com.shadowshiftstudio.jobcentre.domain.model.response.GetJobVacancyResponse
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

    @POST("/employer/add_new_name")
    fun addNewName(@Query("newName") newName: String, @Query("id") id: Long): Call<String>

    @POST("/employer/add_new_address")
    fun addNewAddress(@Query("newAddress") newAddress: String, @Query("id") id: Long): Call<String>

    @POST("/employer/add_photo")
    fun addPhoto(@Query("photo") photo: String, @Query("id") id: Long): Call<String>

    @GET("/employer/get_vacancies")
    fun getVacancies(@Query("username") username: String): Call<List<GetJobVacancyResponse>>

    @GET("/employer/get_applies_vacancies")
    fun getAppliesVacancy(@Query("username") username: String): Call<List<GetAppliesForVacancies>>

    @POST("employer/accept_unemployed")
    fun acceptUnemployed(@Body request: AcceptUnemployedRequest): Call<String>

    @POST("employer/reject_unemployed")
    fun rejectUnemployed(@Body request: AcceptUnemployedRequest): Call<String>
}