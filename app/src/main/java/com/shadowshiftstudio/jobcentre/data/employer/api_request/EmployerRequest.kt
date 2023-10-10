package com.shadowshiftstudio.jobcentre.data.employer.api_request

import android.util.Log
import com.shadowshiftstudio.jobcentre.data.authentication.client.AuthenticationClient
import com.shadowshiftstudio.jobcentre.domain.employer.repository.IEmployerRepository
import com.shadowshiftstudio.jobcentre.domain.model.entity.Employer
import com.shadowshiftstudio.jobcentre.domain.model.request.CreateEmployerRequest
import com.shadowshiftstudio.jobcentre.domain.model.response.AuthenticationResponse
import kotlinx.coroutines.suspendCancellableCoroutine
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.coroutines.resume

class EmployerRequest: IEmployerRepository {
    override suspend fun createEmployer(request: CreateEmployerRequest): Boolean {
        val backendService = AuthenticationClient.employerService

        return suspendCancellableCoroutine { continuation ->
            val call = backendService.createEmployer(request)

            call.enqueue(object : Callback<String> {
                override fun onResponse(
                    call: Call<String>,
                    response: Response<String>
                ) {
                    if (response.isSuccessful) {
                        val responseBody = response.body()

                        if (responseBody != null) {
                            continuation.resume(true)
                        } else {
                            continuation.resume(false)
                        }
                    }
                }

                override fun onFailure(call: Call<String>, t: Throwable) {
                    Log.e("Network client error", t.message ?: "HTTP client failed to connect")
                    continuation.resume(false)
                }
            })

            continuation.invokeOnCancellation {
                call.cancel()
            }
        }
    }

    override suspend fun getEmployerByUsername(username: String): Employer {
        TODO("Not yet implemented")
    }
}