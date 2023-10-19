package com.shadowshiftstudio.jobcentre.data.employer.api_request

import android.util.Log
import com.shadowshiftstudio.jobcentre.data.authentication.client.AuthenticationClient
import com.shadowshiftstudio.jobcentre.data.employer.client.EmployerClient
import com.shadowshiftstudio.jobcentre.domain.employer.repository.IEmployerRepository
import com.shadowshiftstudio.jobcentre.domain.model.entity.Employer
import com.shadowshiftstudio.jobcentre.domain.model.entity.JobVacancy
import com.shadowshiftstudio.jobcentre.domain.model.request.CreateEmployerRequest
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
        val backendService = EmployerClient.employerService
        val buff = listOf<JobVacancy>()
        val voidEmployer = Employer(
            0,
            "",
            "",
            null,
            null,
            null
        )

        return suspendCancellableCoroutine { continuation ->
            val call = backendService.getEmployerByUsername(username)

            call.enqueue(object : Callback<Employer> {
                override fun onResponse(
                    call: Call<Employer>,
                    response: Response<Employer>
                ) {
                    if (response.isSuccessful) {
                        val responseBody = response.body()
                        if (responseBody != null) {
                            continuation.resume(responseBody)
                        } else {
                            Log.e("Response Error", "Response body is null")
                            continuation.resume(voidEmployer)
                        }
                    }
                }

                override fun onFailure(call: Call<Employer>, t: Throwable) {
                    Log.e("Network client error", t.message ?: "HTTP client failed to connect")
                    continuation.resume(voidEmployer)
                }
            })

            continuation.invokeOnCancellation {
                call.cancel()
            }
        }
    }

    override suspend fun addAboutCompany(aboutCompany: String, id: Long): String {
        val backendService = EmployerClient.employerService
        val buff = listOf<JobVacancy>()
        val voidResponse = ""

        return suspendCancellableCoroutine { continuation ->
            val call = backendService.addAboutCompany(aboutCompany, id)

            call.enqueue(object : Callback<String> {
                override fun onResponse(
                    call: Call<String>,
                    response: Response<String>
                ) {
                    if (response.isSuccessful) {
                        val responseBody = response.body()
                        if (responseBody != null) {
                            continuation.resume(voidResponse)
                        } else {
                            Log.e("Response Error", "Response body is null")
                            continuation.resume(voidResponse)
                        }
                    }
                }

                override fun onFailure(call: Call<String>, t: Throwable) {
                    Log.e("Network client error", t.message ?: "HTTP client failed to connect")
                    continuation.resume(voidResponse)
                }
            })

            continuation.invokeOnCancellation {
                call.cancel()
            }
        }
    }
}