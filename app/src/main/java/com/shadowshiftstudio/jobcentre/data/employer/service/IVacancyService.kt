package com.shadowshiftstudio.jobcentre.data.employer.service

import com.shadowshiftstudio.jobcentre.domain.model.entity.JobVacancy
import com.shadowshiftstudio.jobcentre.domain.model.request.CreateJobVacancyRequest
import com.shadowshiftstudio.jobcentre.domain.model.response.GetJobVacancyResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface IVacancyService {
    @POST("/job_vacancy/create")
    fun createJobVacancy(@Body request: CreateJobVacancyRequest): Call<String>

    @GET("/job_vacancy/{job_id}")
    fun getJobVacancy(@Path("job_id") job_id: Long): Call<JobVacancy?>

    @POST("/job_vacancy/apply_vacancy")
    fun applyVacancyUnemployed(@Query("id") id: Long, @Query("username") username: String): Call<String>

    @POST("/job_vacancy/archived_vacancy")
    fun archivedVacancy(@Query("id") id: Long): Call<String>

    @POST("/job_vacancy/update_vacancy")
    fun updateVacancy(@Query("request") request: CreateJobVacancyRequest, @Query("id") id: Long): Call<String>

    @GET("/job_vacancy/get_all_vacancy")
    fun getAllVacancy(): Call<List<GetJobVacancyResponse>>
}