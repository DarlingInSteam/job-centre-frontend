package com.shadowshiftstudio.jobcentre.data.unemployed.api_request

import android.util.Log
import com.shadowshiftstudio.jobcentre.data.authentication.client.AuthenticationClient
import com.shadowshiftstudio.jobcentre.data.employer.client.EmployerClient
import com.shadowshiftstudio.jobcentre.domain.unemployed.repository.IUnemployedRepository
import com.shadowshiftstudio.jobcentre.domain.model.entity.Unemployed
import kotlinx.coroutines.suspendCancellableCoroutine
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.coroutines.resume

class UnemployedRequest: IUnemployedRepository {
    override suspend fun getUnemployedByUsername(username: String): Unemployed {
        TODO("Not yet implemented")
    }

    override suspend fun getUnemployedById(unemployedId: Long): Unemployed? {
        val backendService = EmployerClient.unemployedService

        return suspendCancellableCoroutine { continuation ->
            val call = backendService.getUnemployedId(unemployedId)

            call.enqueue(object : Callback<Unemployed> {
                override fun onResponse(
                    call: Call<Unemployed>,
                    response: Response<Unemployed>
                ) {
                    if (response.isSuccessful) {
                        val responseBody = response.body()

                        if (responseBody != null) {
                            continuation.resume(responseBody)
                        } else {
                            Log.e("Parse error", "")
                        }
                    }
                }

                override fun onFailure(call: Call<Unemployed>, t: Throwable) {
                    Log.e("Network client error", t.message ?: "HTTP client failed to connect")
                }
            })

            continuation.invokeOnCancellation {
                call.cancel()
            }
        }
    }

    override suspend fun getAllUnemployed(): List<Unemployed> {
        val backendService = EmployerClient.unemployedService
        val voidReturn = listOf<Unemployed>()

        return suspendCancellableCoroutine { continuation ->
            val call = backendService.getAllUnemployed()

            call.enqueue(object : Callback<List<Unemployed>> {
                override fun onResponse(
                    call: Call<List<Unemployed>>,
                    response: Response<List<Unemployed>>
                ) {
                    if (response.isSuccessful) {
                        val responseBody = response.body()

                        if (responseBody != null) {
                            continuation.resume(responseBody)
                        } else {
                            continuation.resume(voidReturn)
                        }
                    }
                }

                override fun onFailure(call: Call<List<Unemployed>>, t: Throwable) {
                    Log.e("Network client error", t.message ?: "HTTP client failed to connect")
                    continuation.resume(voidReturn)
                }
            })

            continuation.invokeOnCancellation {
                call.cancel()
            }
        }
    }

    override suspend fun applyUnemployed(unemployedId: Long, vacancyId: Long): String {
        val backendService = EmployerClient.unemployedService

        return suspendCancellableCoroutine { continuation ->
            val call = backendService.inviteUnemployed(unemployedId, vacancyId)

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
                            Log.e("Parse error", "")
                        }
                    }
                }

                override fun onFailure(call: Call<String>, t: Throwable) {
                    Log.e("Network client error", t.message ?: "HTTP client failed to connect")
                }
            })

            continuation.invokeOnCancellation {
                call.cancel()
            }
        }
    }
}