package com.gerosprime.gylog.ui.workouts.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.gerosprime.gylog.base.FetchState
import com.gerosprime.gylog.base.components.viewmodel.BaseViewModel
import com.gerosprime.gylog.models.workouts.detail.LoadWorkoutFromCacheResult
import com.gerosprime.gylog.models.workouts.detail.WorkoutCacheLoader
import io.reactivex.Scheduler
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.functions.Consumer


class DefaultWorkoutDetailViewModel(
    private val workoutDetaiLoader: WorkoutCacheLoader,
    private val uiScheduler: Scheduler?,
    private val backgroundScheduler: Scheduler?
) : BaseViewModel(), WorkoutDetailDialogViewModel {

    private val fetchStateMLD = MutableLiveData<FetchState>()
    private val workoutLoadCacheResultMLD = MutableLiveData<LoadWorkoutFromCacheResult>()

    private val compositeDisposable = CompositeDisposable()

    override fun loadWorkout(workoutRecordId: Long) {
        var loader = workoutDetaiLoader.load(workoutRecordId)

        if (uiScheduler != null) {
            loader = loader.observeOn(uiScheduler)
        }

        if (backgroundScheduler != null) {
            loader = loader.subscribeOn(backgroundScheduler)
        }

        compositeDisposable.add(loader.subscribe(
            Consumer {
            workoutLoadCacheResultMLD.value = it
        }))
    }

    override val fetchStateLD: LiveData<FetchState>
        get() = fetchStateMLD
    override val workoutLoadCacheResultLD: LiveData<LoadWorkoutFromCacheResult>
        get() = workoutLoadCacheResultMLD

}