package com.gerosprime.ui.weight

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.gerosprime.gylog.base.components.android.SingleLiveEvent
import com.gerosprime.gylog.base.components.viewmodel.BaseViewModel
import com.gerosprime.gylog.models.body.weight.*
import io.reactivex.Scheduler
import io.reactivex.Single
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.functions.Consumer
import java.util.*

class DefaultBodyWeightGraphViewModel
    (
     private val bodyWeightLoader : BodyWeightCacheLoader,
     private val bodyWeightSaver : BodyWeightDatabaseSaver,
     private val uiScheduler: Scheduler?,
     private val backgroundScheduler: Scheduler?)
    : BaseViewModel(), BodyWeightGraphViewModel {


    private val bodyWeightDataMLD = MutableLiveData<AllBodyWeightsCacheLoadResult>()
    private val bodyWeightLatestDataMLD = MutableLiveData<LatestBodyWeightLoadResult>()
    private val savedBodyWeightDataMLD = SingleLiveEvent<BodyWeightSaveResult>()

    private val compositeDisposable = CompositeDisposable()

    override fun loadData() {

        val loader = attachAvailableObservers(bodyWeightLoader.loadAll())

        compositeDisposable.add(loader.subscribe(Consumer { bodyWeightDataMLD.value = it }))

    }

    override fun loadLatest() {
        var loader = bodyWeightLoader.loadLatest()
        loader = attachAvailableObservers(loader)

        compositeDisposable.add(loader.subscribe(Consumer { bodyWeightLatestDataMLD.value = it }))
    }

    private fun <T> attachAvailableObservers(singleRxInstance: Single<T>): Single<T> {
        var singleRx = singleRxInstance
        if (uiScheduler != null)
            singleRx = singleRx.observeOn(uiScheduler)

        if (backgroundScheduler != null)
            singleRx = singleRx.subscribeOn(backgroundScheduler)
        return singleRx
    }

    override fun saveBodyWeightLog(recordId: Long?, weight: Float, date: Date, notes: String) {
        val saver = attachAvailableObservers(bodyWeightSaver.save(recordId, weight, date, notes))

        compositeDisposable.add(saver.subscribe(Consumer {
            savedBodyWeightDataMLD.value = it
        }))

    }

    override val bodyWeightDataLiveData: LiveData<AllBodyWeightsCacheLoadResult>
        get() = bodyWeightDataMLD

    override val savedBodyWeightDataLiveData: LiveData<BodyWeightSaveResult>
        get() = savedBodyWeightDataMLD

    override val bodyWeightLatestLiveData: LiveData<LatestBodyWeightLoadResult>
        get() = bodyWeightLatestDataMLD

}