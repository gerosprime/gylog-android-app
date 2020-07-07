package com.gerosprime.ui.fat

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.gerosprime.gylog.base.components.viewmodel.BaseViewModel
import com.gerosprime.gylog.models.body.fat.*
import io.reactivex.Scheduler
import io.reactivex.Single
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.functions.Consumer
import java.util.*

class DefaultBodyFatGraphViewModel
    (
     private val bodyFatLoader : BodyFatCacheLoader,
     private val bodyFatSaver : BodyFatDatabaseSaver,
     private val uiScheduler: Scheduler?,
     private val backgroundScheduler: Scheduler?)
    : BaseViewModel(), BodyFatGraphViewModel {

    private val bodyFatDataMLD = MutableLiveData<AllBodyFatsCacheLoadResult>()
    private val bodyFatLatestDataMLD = MutableLiveData<LatestBodyFatLoadResult>()
    private val savedBodyFatDataMLD = MutableLiveData<BodyFatSaveResult>()

    private val compositeDisposable = CompositeDisposable()


    override fun loadLatest() {
        var loader = bodyFatLoader.loadLatest()
        loader = attachAvailableObservers(loader)

        compositeDisposable.add(loader.subscribe(Consumer { bodyFatLatestDataMLD.value = it }))
    }

    override fun loadData() {

        val loader = attachAvailableObservers(bodyFatLoader.loadAll())
        compositeDisposable.add(loader.subscribe(Consumer { bodyFatDataMLD.value = it }))

    }

    override fun saveBodyWeightLog(recordId: Long?, weight: Float, date: Date, notes: String) {
        val saver = attachAvailableObservers(bodyFatSaver.save(recordId, weight, date, notes))
        compositeDisposable.add(saver.subscribe(Consumer {
            savedBodyFatDataMLD.value = it
        }))

    }

    private fun <T> attachAvailableObservers(singleRxInstance: Single<T>): Single<T> {
        var singleRx = singleRxInstance
        if (uiScheduler != null)
            singleRx = singleRx.observeOn(uiScheduler)

        if (backgroundScheduler != null)
            singleRx = singleRx.subscribeOn(backgroundScheduler)
        return singleRx
    }

    override val bodyFatDataLD: LiveData<AllBodyFatsCacheLoadResult>
        get() = bodyFatDataMLD

    override val savedBodyFatDataLD: LiveData<BodyFatSaveResult>
        get() = savedBodyFatDataMLD

    override val bodyWeightLatestLiveData: LiveData<LatestBodyFatLoadResult>
        get() = bodyFatLatestDataMLD
}