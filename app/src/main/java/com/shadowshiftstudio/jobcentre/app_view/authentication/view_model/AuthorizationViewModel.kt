package com.shadowshiftstudio.jobcentre.app_view.authentication.view_model

import android.content.Context
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class AuthorizationViewModel(private val context: Context): ViewModel() {
    var login: MutableState<String> = mutableStateOf("")
    var password: MutableState<String> = mutableStateOf("")

    val loginStatusLiveData: MutableLiveData<Boolean> = MutableLiveData()


}