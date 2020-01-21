package com.gerosprime.gylog.ui.workouts.history

import androidx.lifecycle.MutableLiveData
import com.gerosprime.gylog.base.FetchState
import com.gerosprime.gylog.models.workouts.history.WorkoutExerciseHistoryResult

interface WorkoutExerciseHistoryViewModel {

    val fetchStateMutableLiveData: MutableLiveData<FetchState>
    val workoutExerciseHistoryMLD : MutableLiveData<WorkoutExerciseHistoryResult>

    fun loadExerciseHistory(workoutId : Long, exerciseid : Long)
}