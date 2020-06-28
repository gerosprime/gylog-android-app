package com.gerosprime.ui.weight

import androidx.lifecycle.LiveData
import com.gerosprime.gylog.models.body.weight.AllBodyWeightsCacheLoadResult
import com.gerosprime.gylog.models.body.weight.BodyWeightSaveResult
import java.util.*

interface BodyWeightGraphViewModel {
    val bodyWeightDataLiveData : LiveData<AllBodyWeightsCacheLoadResult>
    val savedBodyWeightDataLiveData : LiveData<BodyWeightSaveResult>
    fun loadData()
    fun saveBodyWeightLog(recordId: Long?, weight: Float, date: Date, notes: String)
}
