package com.gerosprime.ui.weight

import androidx.lifecycle.MutableLiveData
import com.gerosprime.gylog.models.body.weight.AllBodyWeightsCacheLoadResult
import com.gerosprime.gylog.models.body.weight.BodyWeightSaveResult
import java.util.*

interface BodyWeightGraphViewModel {
    val bodyWeightDataMLD : MutableLiveData<AllBodyWeightsCacheLoadResult>
    val savedBodyWeightDataMLD : MutableLiveData<BodyWeightSaveResult>
    fun loadData()
    fun saveBodyWeightLog(recordId: Long?, weight: Float, date: Date, notes: String)
}
