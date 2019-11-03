package com.gerosprime.gylog.ui.workouts.detail

import androidx.lifecycle.MutableLiveData
import com.gerosprime.gylog.base.FetchState
import com.gerosprime.gylog.models.workouts.detail.LoadWorkoutFromCacheResult


interface WorkoutDetailDialogViewModel {
    val fetchStateMLD : MutableLiveData<FetchState>
    val workoutLoadCacheResultMLD : MutableLiveData<LoadWorkoutFromCacheResult>

    fun loadWorkout(workoutRecordId : Long)

}