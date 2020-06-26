package com.gerosprime.gylog.ui.exercises.dashboard

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.gerosprime.gylog.base.FetchState
import com.gerosprime.gylog.base.components.viewmodel.BaseViewModel
import com.gerosprime.gylog.models.exercises.ExerciseEntity
import com.gerosprime.gylog.models.exercises.ExercisesCacheLoader
import com.gerosprime.gylog.models.exercises.LoadedExercisesResult
import io.reactivex.Scheduler
import io.reactivex.Single
import io.reactivex.disposables.CompositeDisposable

class DefaultDashboardExercisesViewModel(

    private val exercisesCacheLoader: ExercisesCacheLoader,
    private val backgroundScheduler : Scheduler? = null,
    private val uiScheduler: Scheduler? = null
) : BaseViewModel(), DashboardExercisesViewModel {

    private val fetchStateMLD = MutableLiveData<FetchState>()
    private val exercisesMLD = MutableLiveData<List<ExerciseEntity>>()
    private val errorLiveMLD = MutableLiveData<Throwable>()

    private var compositeDisposable = CompositeDisposable()

    override fun loadExercises() {
        var loader : Single<LoadedExercisesResult> = exercisesCacheLoader.loadExercises()
        if (uiScheduler != null)
            loader = loader.observeOn(uiScheduler)

        if (uiScheduler != null)
            loader = loader.subscribeOn(backgroundScheduler)

        fetchStateMLD.value = (FetchState.FETCHING)
        compositeDisposable.add(loader
            .subscribe(this::userProgramsLoaded, this::userProgramsLoadError))
    }

    private fun userProgramsLoaded(result : LoadedExercisesResult) {
        fetchStateMLD.value = FetchState.LOADED
        exercisesMLD.value = result.exercises

    }

    private fun userProgramsLoadError(error : Throwable) {
        fetchStateMLD.value = FetchState.ERROR
        exercisesMLD.value = null
        errorLiveMLD.value = error
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }

    override val fetchStateLiveData: LiveData<FetchState>
        get() = fetchStateMLD
    override val exercisesLiveData: LiveData<List<ExerciseEntity>>
        get() = exercisesMLD
    override val errorLiveData: LiveData<Throwable>
        get() = errorLiveMLD

}