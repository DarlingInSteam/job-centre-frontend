package com.shadowshiftstudio.jobcentre.app.unemployed.view_model

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shadowshiftstudio.jobcentre.data.app.secure_data.SecureStore
import com.shadowshiftstudio.jobcentre.data.employer.api_request.VacancyRequest
import com.shadowshiftstudio.jobcentre.data.unemployed.api_request.UnemployedRequest
import com.shadowshiftstudio.jobcentre.domain.employer.use_case.VacancyUseCase
import com.shadowshiftstudio.jobcentre.domain.model.entity.JobRequirements
import com.shadowshiftstudio.jobcentre.domain.model.entity.JobVacancy
import com.shadowshiftstudio.jobcentre.domain.model.enum.EducationLevel
import com.shadowshiftstudio.jobcentre.domain.model.response.GetJobVacancyResponse
import com.shadowshiftstudio.jobcentre.domain.unemployed.use_case.UnemployedUseCase
import kotlinx.coroutines.launch

class HomeViewModel(private val context: Context): ViewModel() {
    val allVacancies: MutableLiveData<List<GetJobVacancyResponse>> = MutableLiveData()

    var vacancyCard = GetJobVacancyResponse(
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

    private val unemployed: UnemployedUseCase =
        UnemployedUseCase(UnemployedRequest())

    private val vacancy: VacancyUseCase =
        VacancyUseCase(VacancyRequest())

    suspend fun getAllVacancy() {
        viewModelScope.launch {
            var a = vacancy.getAllVacancy()
            allVacancies.value = a
        }
    }

    suspend fun applyVacancy(vacancyId: Long) {
        viewModelScope.launch {
            var a = vacancy.applyVacancyUnemployed(vacancyId, SecureStore.getUsername().toString())
        }
    }
}