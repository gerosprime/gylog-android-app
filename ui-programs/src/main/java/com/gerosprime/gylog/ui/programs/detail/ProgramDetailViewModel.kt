package com.gerosprime.gylog.ui.programs.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.gerosprime.gylog.base.FetchState
import com.gerosprime.gylog.models.programs.detail.LoadProgramFromCacheResult

interface ProgramDetailViewModel {
    val fetchStateLiveData : LiveData<FetchState>
    val programEntityCacheLoadLiveData : LiveData<LoadProgramFromCacheResult>
    fun loadProgramDetail(recordId : Long)
}