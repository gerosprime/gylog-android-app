package com.gerosprime.gylog.ui.programs.detail

import androidx.lifecycle.MutableLiveData
import com.gerosprime.gylog.base.FetchState
import com.gerosprime.gylog.base.components.viewmodel.BaseViewModel
import com.gerosprime.gylog.models.programs.detail.LoadProgramFromCacheResult
import com.gerosprime.gylog.models.programs.detail.ProgramModelCacheLoader
import io.reactivex.Scheduler
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.functions.Consumer

class DefaultProgramDetailViewModel(
    override val fetchStateMLD: MutableLiveData<FetchState>,
    override val programEntityCacheLoadMLD: MutableLiveData<LoadProgramFromCacheResult>,
    private val programModelCacheLoader: ProgramModelCacheLoader,
    private val uiScheduler: Scheduler?,
    private val backgroundScheduler: Scheduler?
) : BaseViewModel(), ProgramDetailViewModel {

    private val compositeDisposable  = CompositeDisposable()

    override fun loadProgramDetail(recordId: Long) {
        var loader = programModelCacheLoader.loadFromCache(recordId)

        if (uiScheduler != null)
            loader = loader.observeOn(uiScheduler)

        if (backgroundScheduler != null) {
            loader = loader.subscribeOn(backgroundScheduler)
        }

        compositeDisposable.add(loader.subscribe(Consumer {
            programEntityCacheLoadMLD.value = it
        }))

    }

}