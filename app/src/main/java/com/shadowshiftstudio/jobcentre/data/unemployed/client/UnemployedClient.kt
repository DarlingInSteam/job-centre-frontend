package com.shadowshiftstudio.jobcentre.data.unemployed.client

import com.shadowshiftstudio.jobcentre.data.app.secure_data.SecureStore
import com.shadowshiftstudio.jobcentre.data.authentication.api_request.AuthenticationRequest
import com.shadowshiftstudio.jobcentre.domain.authentication.use_case.AuthenticationUseCase
import kotlinx.coroutines.runBlocking
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.net.HttpURLConnection

object UnemployedClient {
    private val authenticationUseCase: AuthenticationUseCase =
        AuthenticationUseCase(AuthenticationRequest())

    private var client =
        OkHttpClient.Builder()
            .addInterceptor { chain ->
                val request = chain.request().newBuilder()
                    .addHeader("Authorization", "Bearer ${SecureStore.getAccessToken()}")
                    .build()
                chain.proceed(request)
            }
            .addInterceptor { chain ->
                val request = chain.request()
                var a: Boolean? = false
                val response = chain.proceed(request)

                if (response.code == HttpURLConnection.HTTP_UNAUTHORIZED || response.code == HttpURLConnection.HTTP_FORBIDDEN) {
                    a = runBlocking {
                        authenticationUseCase.getRefreshToken()
                    }

                    if (a == true) {
                        val newRequest = request.newBuilder()
                            .addHeader("Authorization", "Bearer ${SecureStore.getAccessToken()}")
                            .build()

                        return@addInterceptor chain.proceed(newRequest)
                    }
                }

                return@addInterceptor response
            }
            .build()

    private val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl("http://192.168.86.215:8080")
        .addConverterFactory(GsonConverterFactory.create())
        .client(client)
        .build()
}