package com.gerosprime.gylog.ui.programs

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.gerosprime.gylog.base.FetchState
import com.gerosprime.gylog.base.components.viewmodel.BaseViewModel
import com.gerosprime.gylog.models.programs.LoadedProgramResult
import com.gerosprime.gylog.models.programs.ProgramEntity
import com.gerosprime.gylog.models.programs.ProgramsLoader
import io.reactivex.Scheduler
import io.reactivex.Single
import io.reactivex.disposables.CompositeDisposable

class DefaultProgramsDashboardViewModel constructor(
    override val fetchStateLiveData: MutableLiveData<FetchState>,
    override val userProgramListLiveData: MutableLiveData<List<ProgramEntity>>,
    override val builtInProgramsLiveData: MutableLiveData<List<ProgramEntity>>,
    override val errorLiveData: MutableLiveData<Throwable>,

    private val programsLoader: ProgramsLoader,
    private val backgroundScheduler : Scheduler? = null,
    private val uiScheduler: Scheduler? = null

) : BaseViewModel(), ProgramsDashboardViewModel {

    private var compositeDisposable : CompositeDisposable = CompositeDisposable()

    override fun loadUserPrograms() {

        var loader : Single<LoadedProgramResult> = programsLoader.loadUserPrograms()
        if (uiScheduler != null)
            loader = loader.observeOn(uiScheduler)

        if (uiScheduler != null)
            loader = loader.subscribeOn(backgroundScheduler)

        fetchStateLiveData.value = (FetchState.FETCHING)
        compositeDisposable.add(loader
            .subscribe(this::userProgramsLoaded, this::userProgramsLoadError))
    }

    private fun userProgramsLoaded(result : LoadedProgramResult) {
        fetchStateLiveData.value = FetchState.LOADED
        userProgramListLiveData.value = result.userPrograms
        builtInProgramsLiveData.value = result.builtInPrograms

    }

    private fun userProgramsLoadError(error : Throwable) {
        fetchStateLiveData.value = FetchState.ERROR
        userProgramListLiveData.value = null
        builtInProgramsLiveData.value = null
        errorLiveData.value = error
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }
}