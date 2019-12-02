package com.gerosprime.ui.fat

import androidx.lifecycle.MutableLiveData
import com.gerosprime.gylog.models.body.fat.AllBodyFatsCacheLoadResult
import com.gerosprime.gylog.models.body.fat.BodyFatSaveResult
import java.util.*

interface BodyFatGraphViewModel {
    val bodyFatDataMLD : MutableLiveData<AllBodyFatsCacheLoadResult>
    val savedBodyFatDataMLD : MutableLiveData<BodyFatSaveResult>
    fun loadData()
    fun saveBodyWeightLog(recordId: Long?, weight: Float, date: Date, notes: String)
}
