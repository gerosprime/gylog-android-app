package com.gerosprime.ui.fat

import androidx.lifecycle.MutableLiveData
import com.gerosprime.gylog.base.components.viewmodel.BaseViewModel
import com.gerosprime.gylog.models.body.fat.AllBodyFatsCacheLoadResult
import com.gerosprime.gylog.models.body.fat.BodyFatCacheLoader
import com.gerosprime.gylog.models.body.fat.BodyFatDatabaseSaver
import com.gerosprime.gylog.models.body.fat.BodyFatSaveResult
import io.reactivex.Scheduler
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.functions.Consumer
import java.util.*

class DefaultBodyFatGraphViewModel
    (override val bodyFatDataMLD: MutableLiveData<AllBodyFatsCacheLoadResult>,
     override val savedBodyFatDataMLD: MutableLiveData<BodyFatSaveResult>,
     private val bodyFatLoader : BodyFatCacheLoader,
     private val bodyFatSaver : BodyFatDatabaseSaver,
     private val uiScheduler: Scheduler?,
     private val backgroundScheduler: Scheduler?)
    : BaseViewModel(), BodyFatGraphViewModel {

    private val compositeDisposable = CompositeDisposable()

    override fun loadData() {

        var loader = bodyFatLoader.loadAll()
        if (uiScheduler != null)
            loader = loader.observeOn(uiScheduler)

        if (backgroundScheduler != null)
            loader = loader.subscribeOn(backgroundScheduler)

        compositeDisposable.add(loader.subscribe(Consumer { bodyFatDataMLD.value = it }))

    }

    override fun saveBodyWeightLog(recordId: Long?, weight: Float, date: Date, notes: String) {
        var saver = bodyFatSaver.save(recordId, weight, date, notes)

        if (uiScheduler != null)
            saver = saver.observeOn(uiScheduler)

        if (backgroundScheduler != null)
            saver = saver.subscribeOn(backgroundScheduler)

        compositeDisposable.add(saver.subscribe(Consumer {
            savedBodyFatDataMLD.value = it
        }))

    }
}