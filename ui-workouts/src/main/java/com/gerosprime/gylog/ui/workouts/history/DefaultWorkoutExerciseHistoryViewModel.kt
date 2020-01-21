package com.gerosprime.gylog.ui.workouts.history

import androidx.lifecycle.MutableLiveData
import com.gerosprime.gylog.base.FetchState
import com.gerosprime.gylog.base.components.viewmodel.BaseViewModel
import com.gerosprime.gylog.models.workouts.history.WorkoutExerciseHistoryLoader
import com.gerosprime.gylog.models.workouts.history.WorkoutExerciseHistoryResult
import io.reactivex.Scheduler
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.functions.Consumer

class DefaultWorkoutExerciseHistoryViewModel(
    override val fetchStateMutableLiveData: MutableLiveData<FetchState>,
    override val workoutExerciseHistoryMLD: MutableLiveData<WorkoutExerciseHistoryResult>,
    private val workoutExerciseHistoryLoader: WorkoutExerciseHistoryLoader,

    private val uiScheduler: Scheduler?,
    private val backgroundScheduler: Scheduler?

) : BaseViewModel(), WorkoutExerciseHistoryViewModel {

    private val compositeDisposable = CompositeDisposable()

    override fun loadExerciseHistory(workoutId: Long, exerciseid: Long) {

        var loader = workoutExerciseHistoryLoader.load(workoutId, exerciseid)

        if (uiScheduler != null)
            loader = loader.observeOn(uiScheduler)

        if (backgroundScheduler != null)
            loader = loader.subscribeOn(backgroundScheduler)

        compositeDisposable.add(loader.subscribe(Consumer {
            workoutExerciseHistoryMLD.value = it
        }))
    }
}