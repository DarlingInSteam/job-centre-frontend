package com.shadowshiftstudio.jobcentre.data.unemployed.service

import com.shadowshiftstudio.jobcentre.domain.model.entity.Unemployed
import retrofit2.Call
import retrofit2.http.GET

interface IUnemployedService {
    @GET("/unemployed/get_all_unemployed")
    fun getAllUnemployed(): Call<List<Unemployed>>
}