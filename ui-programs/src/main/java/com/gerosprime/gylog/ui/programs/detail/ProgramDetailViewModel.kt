package com.gerosprime.gylog.ui.programs.detail

import androidx.lifecycle.MutableLiveData
import com.gerosprime.gylog.base.FetchState
import com.gerosprime.gylog.models.programs.detail.LoadProgramFromCacheResult

interface ProgramDetailViewModel {
    val fetchStateMLD : MutableLiveData<FetchState>
    val programEntityCacheLoadMLD : MutableLiveData<LoadProgramFromCacheResult>
    fun loadProgramDetail(recordId : Long)
}