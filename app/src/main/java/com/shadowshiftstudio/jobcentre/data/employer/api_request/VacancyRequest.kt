package com.shadowshiftstudio.jobcentre.data.employer.api_request

import android.util.Log
import com.shadowshiftstudio.jobcentre.data.authentication.client.AuthenticationClient
import com.shadowshiftstudio.jobcentre.data.employer.client.EmployerClient
import com.shadowshiftstudio.jobcentre.domain.employer.repository.IVacancyRepository
import com.shadowshiftstudio.jobcentre.domain.model.entity.JobVacancy
import com.shadowshiftstudio.jobcentre.domain.model.request.CreateJobVacancyRequest
import kotlinx.coroutines.suspendCancellableCoroutine
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.coroutines.resume

class VacancyRequest: IVacancyRepository {
    override suspend fun createVacancy(request: CreateJobVacancyRequest): String {
        val backendService = EmployerClient.vacancyService

        return suspendCancellableCoroutine { continuation ->
            val call = backendService.createJobVacancy(request)

            call.enqueue(object : Callback<String> {
                override fun onResponse(
                    call: Call<String>,
                    response: Response<String>
                ) {
                    if (response.isSuccessful) {
                        val responseBody = response.body()

                        if (responseBody != null) {
                            continuation.resume(responseBody)
                        } else {
                            continuation.resume("")
                        }
                    }
                }

                override fun onFailure(call: Call<String>, t: Throwable) {
                    Log.e("Network client error", t.message ?: "HTTP client failed to connect")
                    continuation.resume("")
                }
            })

            continuation.invokeOnCancellation {
                call.cancel()
            }
        }
    }

    override suspend fun getVacancy(job_id: Long): JobVacancy? {
        val backendService = EmployerClient.vacancyService

        return suspendCancellableCoroutine { continuation ->
            val call = backendService.getJobVacancy(job_id)

            call.enqueue(object : Callback<JobVacancy?> {
                override fun onResponse(
                    call: Call<JobVacancy?>,
                    response: Response<JobVacancy?>
                ) {
                    if (response.isSuccessful) {
                        val responseBody = response.body()

                        if (responseBody != null) {
                            continuation.resume(responseBody)
                        } else {
                        }
                    }
                }

                override fun onFailure(call: Call<JobVacancy?>, t: Throwable) {
                    Log.e("Network client error", t.message ?: "HTTP client failed to connect")
                }
            })

            continuation.invokeOnCancellation {
                call.cancel()
            }
        }
    }

    override suspend fun applyVacancyUnemployed(vacancyId: Long, username: String): String {
        TODO("Not yet implemented")
    }

    override suspend fun archivedVacancy(vacancyId: Long): String {
        TODO("Not yet implemented")
    }

    override suspend fun updateVacancy(request: CreateJobVacancyRequest, vacancyId: Long): String {
        TODO("Not yet implemented")
    }
}