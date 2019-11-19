package com.gerosprime.gylog.ui.exercises.dashboard

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
    override val fetchStateLiveData: MutableLiveData<FetchState>,
    override val exercisesLiveData: MutableLiveData<List<ExerciseEntity>>,
    override val errorLiveData: MutableLiveData<Throwable>,

    private val exercisesCacheLoader: ExercisesCacheLoader,
    private val backgroundScheduler : Scheduler? = null,
    private val uiScheduler: Scheduler? = null
) : BaseViewModel(), DashboardExercisesViewModel {

    private var compositeDisposable : CompositeDisposable = CompositeDisposable()

    override fun loadExercises() {
        var loader : Single<LoadedExercisesResult> = exercisesCacheLoader.loadExercises()
        if (uiScheduler != null)
            loader = loader.observeOn(uiScheduler)

        if (uiScheduler != null)
            loader = loader.subscribeOn(backgroundScheduler)

        fetchStateLiveData.value = (FetchState.FETCHING)
        compositeDisposable.add(loader
            .subscribe(this::userProgramsLoaded, this::userProgramsLoadError))
    }

    private fun userProgramsLoaded(result : LoadedExercisesResult) {
        fetchStateLiveData.value = FetchState.LOADED
        exercisesLiveData.value = result.exercises

    }

    private fun userProgramsLoadError(error : Throwable) {
        fetchStateLiveData.value = FetchState.ERROR
        exercisesLiveData.value = null
        errorLiveData.value = error
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }

}