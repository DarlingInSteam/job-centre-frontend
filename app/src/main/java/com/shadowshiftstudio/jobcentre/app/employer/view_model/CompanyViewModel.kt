package com.shadowshiftstudio.jobcentre.app.employer.view_model

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shadowshiftstudio.jobcentre.data.app.secure_data.SecureStore
import com.shadowshiftstudio.jobcentre.data.employer.api_request.EmployerRequest
import com.shadowshiftstudio.jobcentre.domain.employer.use_case.EmployerUseCase
import com.shadowshiftstudio.jobcentre.domain.model.entity.Employer
import kotlinx.coroutines.launch

class CompanyViewModel(private val context: Context): ViewModel() {
    val employer: MutableLiveData<Employer> = MutableLiveData()

    private val employerUseCase =
        EmployerUseCase(EmployerRequest())

    suspend fun getEmployer() {
        viewModelScope.launch {
            val result = employerUseCase.getEmployerByUsername(SecureStore.getUsername().toString())

            employer.value = result
        }.join()
    }

    suspend fun addAboutCompany(aboutCompany: String, id: Long) {
        viewModelScope.launch {
            val result = employerUseCase.addAboutCompany(aboutCompany, id)

            getEmployer()
        }.join()
    }

    suspend fun addNewName(newName: String, id: Long) {
        viewModelScope.launch {
            val result = employerUseCase.addNewName(newName, id)

            getEmployer()
        }.join()
    }

    suspend fun addNewAddress(newAddress: String, id: Long) {
        viewModelScope.launch {
            val result = employerUseCase.addNewAddress(newAddress, id)

            getEmployer()
        }.join()
    }

    suspend fun addPhoto(photo: String, id: Long) {
        viewModelScope.launch {
            val result = employerUseCase.addPhoto(photo, id)

            getEmployer()
        }.join()
    }
}