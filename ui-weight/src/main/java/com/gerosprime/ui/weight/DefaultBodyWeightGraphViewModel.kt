package com.gerosprime.ui.weight

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.gerosprime.gylog.base.components.android.SingleLiveEvent
import com.gerosprime.gylog.base.components.viewmodel.BaseViewModel
import com.gerosprime.gylog.models.body.weight.AllBodyWeightsCacheLoadResult
import com.gerosprime.gylog.models.body.weight.BodyWeightCacheLoader
import com.gerosprime.gylog.models.body.weight.BodyWeightDatabaseSaver
import com.gerosprime.gylog.models.body.weight.BodyWeightSaveResult
import io.reactivex.Scheduler
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
    private val savedBodyWeightDataMLD = SingleLiveEvent<BodyWeightSaveResult>()

    private val compositeDisposable = CompositeDisposable()

    override fun loadData() {

        var loader = bodyWeightLoader.loadAll()
        if (uiScheduler != null)
            loader = loader.observeOn(uiScheduler)

        if (backgroundScheduler != null)
            loader = loader.subscribeOn(backgroundScheduler)

        compositeDisposable.add(loader.subscribe(Consumer { bodyWeightDataMLD.value = it }))

    }

    override fun saveBodyWeightLog(recordId: Long?, weight: Float, date: Date, notes: String) {
        var saver = bodyWeightSaver.save(recordId, weight, date, notes)

        if (uiScheduler != null)
            saver = saver.observeOn(uiScheduler)

        if (backgroundScheduler != null)
            saver = saver.subscribeOn(backgroundScheduler)

        compositeDisposable.add(saver.subscribe(Consumer {
            savedBodyWeightDataMLD.value = it
        }))

    }

    override val bodyWeightDataLiveData: LiveData<AllBodyWeightsCacheLoadResult>
        get() = bodyWeightDataMLD

    override val savedBodyWeightDataLiveData: LiveData<BodyWeightSaveResult>
        get() = savedBodyWeightDataMLD

}