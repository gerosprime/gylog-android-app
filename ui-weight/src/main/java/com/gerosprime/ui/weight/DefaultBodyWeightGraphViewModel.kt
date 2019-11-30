package com.gerosprime.ui.weight

import androidx.lifecycle.MutableLiveData
import com.gerosprime.gylog.base.components.viewmodel.BaseViewModel
import com.gerosprime.gylog.models.body.weight.AllBodyWeightsCacheLoadResult
import com.gerosprime.gylog.models.body.weight.BodyWeightCacheLoadResult
import com.gerosprime.gylog.models.body.weight.BodyWeightCacheLoader
import io.reactivex.Scheduler
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.functions.Consumer

class DefaultBodyWeightGraphViewModel
    (override val bodyWeightDataMLD: MutableLiveData<AllBodyWeightsCacheLoadResult>,
     private val bodyWeightLoader : BodyWeightCacheLoader,
     private val uiScheduler: Scheduler?,
     private val backgroundScheduler: Scheduler?)
    : BaseViewModel(), BodyWeightGraphViewModel {

    private val compositeDisposable = CompositeDisposable()

    override fun loadData() {

        var loader = bodyWeightLoader.loadAll()
        if (uiScheduler != null)
            loader = loader.observeOn(uiScheduler)

        if (backgroundScheduler != null)
            loader = loader.subscribeOn(backgroundScheduler)

        compositeDisposable.add(loader.subscribe(Consumer { bodyWeightDataMLD.value = it }))

    }

}