package com.gerosprime.gylog.ui.workouts.detail

import androidx.lifecycle.LiveData
import com.gerosprime.gylog.base.FetchState
import com.gerosprime.gylog.models.workouts.detail.LoadWorkoutFromCacheResult


interface WorkoutDetailDialogViewModel {
    val fetchStateLD : LiveData<FetchState>
    val workoutLoadCacheResultLD : LiveData<LoadWorkoutFromCacheResult>

    fun loadWorkout(workoutRecordId : Long)

}