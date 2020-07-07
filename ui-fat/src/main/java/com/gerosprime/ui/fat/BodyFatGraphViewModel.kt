package com.gerosprime.ui.fat

import androidx.lifecycle.LiveData
import com.gerosprime.gylog.models.body.fat.AllBodyFatsCacheLoadResult
import com.gerosprime.gylog.models.body.fat.BodyFatSaveResult
import com.gerosprime.gylog.models.body.fat.LatestBodyFatLoadResult
import java.util.*

interface BodyFatGraphViewModel {
    val bodyFatDataLD : LiveData<AllBodyFatsCacheLoadResult>
    val bodyWeightLatestLiveData : LiveData<LatestBodyFatLoadResult>
    val savedBodyFatDataLD : LiveData<BodyFatSaveResult>
    fun loadData()
    fun loadLatest()
    fun saveBodyWeightLog(recordId: Long?, weight: Float, date: Date, notes: String)
}
