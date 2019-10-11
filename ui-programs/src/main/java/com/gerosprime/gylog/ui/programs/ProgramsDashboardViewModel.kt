package com.gerosprime.gylog.ui.programs

import androidx.lifecycle.MutableLiveData
import com.gerosprime.gylog.base.FetchState
import com.gerosprime.gylog.models.programs.ProgramEntity

interface ProgramsDashboardViewModel {
    val fetchStateLiveData: MutableLiveData<FetchState>
    val userProgramListLiveData: MutableLiveData<List<ProgramEntity>>
    val builtInProgramsLiveData : MutableLiveData<List<ProgramEntity>>
    val errorLiveData : MutableLiveData<Throwable>
    fun loadUserPrograms()
}