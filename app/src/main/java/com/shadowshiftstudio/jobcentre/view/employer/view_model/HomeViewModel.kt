package com.shadowshiftstudio.jobcentre.view.employer.view_model

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shadowshiftstudio.jobcentre.data.unemployed.api_request.UnemployedRequest
import com.shadowshiftstudio.jobcentre.domain.unemployed.use_case.UnemployedUseCase
import com.shadowshiftstudio.jobcentre.model.entity.Unemployed
import kotlinx.coroutines.launch

class HomeViewModel(private val context: Context): ViewModel() {
    val allUnemployed: MutableLiveData<List<Unemployed>> = MutableLiveData()

    private val unemployed: UnemployedUseCase =
        UnemployedUseCase(UnemployedRequest())

    suspend fun getAllUnemployed() {
        viewModelScope.launch {
            val result = unemployed.getAllUnemployed()

            allUnemployed.value = result
        }.join()
    }
}