package com.shadowshiftstudio.jobcentre.app.employer.view_model

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shadowshiftstudio.jobcentre.data.app.secure_data.SecureStore
import com.shadowshiftstudio.jobcentre.data.employer.api_request.EmployerRequest
import com.shadowshiftstudio.jobcentre.domain.employer.use_case.EmployerUseCase
import com.shadowshiftstudio.jobcentre.domain.model.entity.JobRequirements
import com.shadowshiftstudio.jobcentre.domain.model.entity.JobVacancy
import com.shadowshiftstudio.jobcentre.domain.model.entity.Unemployed
import com.shadowshiftstudio.jobcentre.domain.model.enum.EducationLevel
import com.shadowshiftstudio.jobcentre.domain.model.request.AcceptUnemployedRequest
import com.shadowshiftstudio.jobcentre.domain.model.response.GetAppliesForVacancies
import com.shadowshiftstudio.jobcentre.domain.model.response.GetJobVacancyResponse
import kotlinx.coroutines.launch

class VacancyScreenViewModel(private val context: Context) : ViewModel() {
    val applies: MutableLiveData<GetAppliesForVacancies> = MutableLiveData()

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

    suspend fun getAppliesVacancy() {
        viewModelScope.launch {
            val res = employerUseCase.getAppliesVacancy(SecureStore.getUsername().toString())
            val buff = mutableListOf<GetAppliesForVacancies>()
            for (apply in res) {
                if (apply.jobVacanciesDto.id == vacancy.jobVacancy.id) {
                    buff.add(apply)
                }
            }
            if (buff.size > 0) applies.value = buff[0]
        }
    }

    suspend fun acceptUnemployed(unemployedId: Long, vacancyId: Long) {
        viewModelScope.launch {
            val res = employerUseCase.acceptUnemployed(AcceptUnemployedRequest(vacancyId, SecureStore.getUsername().toString(), unemployedId))
        }
    }

    suspend fun rejectUnemployed(unemployedId: Long, vacancyId: Long) {
        viewModelScope.launch {
            val res = employerUseCase.rejectUnemployed(AcceptUnemployedRequest(vacancyId, SecureStore.getUsername().toString(), unemployedId))
        }
    }
}