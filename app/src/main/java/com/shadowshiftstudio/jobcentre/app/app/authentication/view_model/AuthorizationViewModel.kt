package com.shadowshiftstudio.jobcentre.app.app.authentication.view_model

import android.content.Context
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shadowshiftstudio.jobcentre.data.authentication.api_request.AuthenticationRequest
import com.shadowshiftstudio.jobcentre.domain.authentication.use_case.AuthenticationUseCase
import kotlinx.coroutines.launch

class AuthorizationViewModel(private val context: Context): ViewModel() {
    var login: MutableState<String> = mutableStateOf("")
    var password: MutableState<String> = mutableStateOf("")
    var phone: MutableState<String> = mutableStateOf("")

    val loginStatusLiveData: MutableLiveData<Boolean> = MutableLiveData()

    private val authenticationUseCase: AuthenticationUseCase =
        AuthenticationUseCase(AuthenticationRequest())

    suspend fun login() {
        viewModelScope.launch {
            val authenticationRequest =
                com.shadowshiftstudio.jobcentre.domain.model.request.AuthenticationRequest(
                    login.value,
                    phone.value,
                    password.value
                )
            var status = authenticationUseCase.loginUser(authenticationRequest)
        }
    }
}