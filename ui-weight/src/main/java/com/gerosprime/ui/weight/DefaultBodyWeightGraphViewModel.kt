package com.gerosprime.ui.weight

import androidx.lifecycle.MutableLiveData
import com.gerosprime.gylog.base.components.viewmodel.BaseViewModel
import com.gerosprime.gylog.models.body.weight.*
import io.reactivex.Scheduler
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.functions.Consumer
import java.util.*

class DefaultBodyWeightGraphViewModel
    (override val bodyWeightDataMLD: MutableLiveData<AllBodyWeightsCacheLoadResult>,
     override val savedBodyWeightDataMLD: MutableLiveData<BodyWeightSaveResult>,
     private val bodyWeightLoader : BodyWeightCacheLoader,
     private val bodyWeightSaver : BodyWeightDatabaseSaver,
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
}