package com.gerosprime.ui.weight

import androidx.lifecycle.MutableLiveData
import com.gerosprime.gylog.models.body.weight.AllBodyWeightsCacheLoadResult

interface BodyWeightGraphViewModel {
    val bodyWeightDataMLD : MutableLiveData<AllBodyWeightsCacheLoadResult>
    fun loadData()
}
