package com.shadowshiftstudio.jobcentre.data.authentication.api_request

import android.util.Log
import com.shadowshiftstudio.jobcentre.data.app.secure_data.SecureStore
import com.shadowshiftstudio.jobcentre.data.authentication.client.AuthenticationClient
import com.shadowshiftstudio.jobcentre.domain.authentication.repository.IAuthenticationRepository
import com.shadowshiftstudio.jobcentre.model.request.AuthenticationRequest
import com.shadowshiftstudio.jobcentre.model.request.RefreshTokenRequest
import com.shadowshiftstudio.jobcentre.model.request.RegisterRequest
import com.shadowshiftstudio.jobcentre.model.response.AuthenticationResponse
import kotlinx.coroutines.suspendCancellableCoroutine
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.coroutines.resume

class AuthenticationRequest: IAuthenticationRepository {
    override suspend fun registerUser(registerRequest: RegisterRequest): Boolean {
        val backendService = AuthenticationClient.authenticationService

        return suspendCancellableCoroutine { continuation ->
            val call = backendService.registration(registerRequest)

            call.enqueue(object : Callback<AuthenticationResponse> {
                override fun onResponse(
                    call: Call<AuthenticationResponse>,
                    response: Response<AuthenticationResponse>
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

                override fun onFailure(call: Call<AuthenticationResponse>, t: Throwable) {
                    Log.e("Network client error", t.message ?: "HTTP client failed to connect")
                    continuation.resume(false)
                }
            })

            continuation.invokeOnCancellation {
                call.cancel()
            }
        }
    }

    override suspend fun loginUser(authenticationRequest: AuthenticationRequest): Boolean {
        val backendService = AuthenticationClient.authenticationService

        return suspendCancellableCoroutine { continuation ->
            val call = backendService.login(authenticationRequest)

            call.enqueue(object : Callback<AuthenticationResponse> {
                override fun onResponse(
                    call: Call<AuthenticationResponse>,
                    response: Response<AuthenticationResponse>
                ) {
                    if (response.isSuccessful) {
                        val responseBody = response.body()

                        if (responseBody != null) {

                            SecureStore.putAccessToken(responseBody.accessToken)
                            SecureStore.putUpdateToken(responseBody.token)
                            SecureStore.putUsername(responseBody.username)
                            SecureStore.putUserRole(responseBody.role)
                            SecureStore.putIsLogin()

                            continuation.resume(true)
                        } else {
                            continuation.resume(false)
                        }
                    }
                }

                override fun onFailure(call: Call<AuthenticationResponse>, t: Throwable) {
                    Log.e("Network client error", t.message ?: "HTTP client failed to connect")
                    continuation.resume(false)
                }
            })
        }
    }

    override suspend fun getRefreshToken(): Boolean {
        val backendService = AuthenticationClient.authenticationService

        return suspendCancellableCoroutine { continuation ->
            val token = SecureStore.getUpdateToken().toString()
            val refreshTokenRequest = RefreshTokenRequest(token, SecureStore.getUsername().toString())
            val call = backendService.getRefresh(refreshTokenRequest)

            call.enqueue(object : Callback<AuthenticationResponse> {
                override fun onResponse(
                    call: Call<AuthenticationResponse>,
                    response: Response<AuthenticationResponse>
                ) {
                    if (response.isSuccessful) {
                        val responseBody = response.body()

                        if (responseBody != null) {

                            SecureStore.putAccessToken(responseBody.accessToken)
                            SecureStore.putUpdateToken(responseBody.token)

                            continuation.resume(true)
                        } else {
                            continuation.resume(false)
                        }
                    }
                }

                override fun onFailure(call: Call<AuthenticationResponse>, t: Throwable) {
                    Log.e("Network client error", t.message ?: "HTTP client failed to connect")
                    continuation.resume(false)
                }
            })
        }
    }

}