package com.shadowshiftstudio.jobcentre.data.unemployed.service

import com.shadowshiftstudio.jobcentre.domain.model.entity.Unemployed
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface IUnemployedService {
    @GET("/unemployed/get_all_unemployed")
    fun getAllUnemployed(): Call<List<Unemployed>>

    @GET("/unemployed/get_unemployed_id")
    fun getUnemployedId(@Query("unemployedId") unemployedId: Long): Call<Unemployed>

    @POST("/unemployed/invite_unemployed")
    fun inviteUnemployed(@Query("unemployedId") unemployedId: Long, @Query("vacancyId") vacancyId: Long): Call<String>
}