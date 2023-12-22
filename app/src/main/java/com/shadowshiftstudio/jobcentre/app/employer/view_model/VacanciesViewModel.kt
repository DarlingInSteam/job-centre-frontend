package com.shadowshiftstudio.jobcentre.app.employer.view_model

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shadowshiftstudio.jobcentre.data.app.secure_data.SecureStore
import com.shadowshiftstudio.jobcentre.data.employer.api_request.EmployerRequest
import com.shadowshiftstudio.jobcentre.data.employer.api_request.VacancyRequest
import com.shadowshiftstudio.jobcentre.domain.employer.use_case.EmployerUseCase
import com.shadowshiftstudio.jobcentre.domain.employer.use_case.VacancyUseCase
import com.shadowshiftstudio.jobcentre.domain.model.entity.JobRequirements
import com.shadowshiftstudio.jobcentre.domain.model.entity.JobVacancy
import com.shadowshiftstudio.jobcentre.domain.model.enum.EducationLevel
import com.shadowshiftstudio.jobcentre.domain.model.request.CreateJobVacancyRequest
import com.shadowshiftstudio.jobcentre.domain.model.response.GetJobVacancyResponse
import kotlinx.coroutines.launch

class VacanciesViewModel(private val context: Context) : ViewModel() {
    val allVacancies: MutableLiveData<List<GetJobVacancyResponse>> = MutableLiveData()

    var vacancy = GetJobVacancyResponse(
        JobVacancy(
            0,
            "",
            "",
            0,
            false
        ),
        JobRequirements(
            0,
            EducationLevel.MASTER,
            0,
            0,
            0
        )
    )

    private val employerUseCase =
        EmployerUseCase(EmployerRequest())

    private val vacancyUseCase =
        VacancyUseCase(VacancyRequest())

    suspend fun getVacancies() {
        viewModelScope.launch {
            val vacancies = employerUseCase.getJobVacancies(SecureStore.getUsername().toString())

            allVacancies.value = vacancies
        }.join()
    }

    suspend fun createVacancy(request: CreateJobVacancyRequest) {
        viewModelScope.launch {
            val result = vacancyUseCase.createVacancy(request)
            getVacancies()
        }.join()
    }
}