package com.gerosprime.gylog.ui.exercises.dashboard

import androidx.lifecycle.MutableLiveData
import com.gerosprime.gylog.base.FetchState
import com.gerosprime.gylog.models.exercises.ExerciseEntity

interface DashboardExercisesViewModel {
    val fetchStateLiveData: MutableLiveData<FetchState>
    val exercisesLiveData: MutableLiveData<List<ExerciseEntity>>
    val errorLiveData : MutableLiveData<Throwable>
    fun loadExercises()
}