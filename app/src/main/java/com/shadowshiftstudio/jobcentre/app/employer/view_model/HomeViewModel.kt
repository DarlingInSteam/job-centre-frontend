package com.shadowshiftstudio.jobcentre.app.employer.view_model

import android.content.Context
import android.text.style.TtsSpan.DateBuilder
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shadowshiftstudio.jobcentre.data.unemployed.api_request.UnemployedRequest
import com.shadowshiftstudio.jobcentre.domain.model.entity.Ability
import com.shadowshiftstudio.jobcentre.domain.model.entity.JobVacancy
import com.shadowshiftstudio.jobcentre.domain.unemployed.use_case.UnemployedUseCase
import com.shadowshiftstudio.jobcentre.domain.model.entity.Unemployed
import com.shadowshiftstudio.jobcentre.domain.model.enum.EducationLevel
import kotlinx.coroutines.launch
import java.util.Date

class HomeViewModel(private val context: Context): ViewModel() {
    val allUnemployed: MutableLiveData<List<Unemployed>> = MutableLiveData()
    var unemployedFullScreen: Unemployed = Unemployed(
        0,
        0,
        "",
        0,
        EducationLevel.BACHELOR,
        "",
        "",
        "",
        0,
        Date(123),
        listOf<Ability>(),
        "",
        ""
    )

    private val unemployed: UnemployedUseCase =
        UnemployedUseCase(UnemployedRequest())

    suspend fun getAllUnemployed() {
        viewModelScope.launch {
            val result = unemployed.getAllUnemployed()

            allUnemployed.value = result
        }.join()
    }

    suspend fun inviteUnemployed(unemployedId: Long, vacancyId: Long) {
        viewModelScope.launch {
            val res = unemployed.applyUnemployed(unemployedId, vacancyId)
        }
    }
}