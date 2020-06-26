package com.gerosprime.gylog.ui.exercises.dashboard

import androidx.lifecycle.LiveData
import com.gerosprime.gylog.base.FetchState
import com.gerosprime.gylog.models.exercises.ExerciseEntity

interface DashboardExercisesViewModel {
    val fetchStateLiveData: LiveData<FetchState>
    val exercisesLiveData: LiveData<List<ExerciseEntity>>
    val errorLiveData : LiveData<Throwable>
    fun loadExercises()
}