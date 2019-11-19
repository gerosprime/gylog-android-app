package com.gerosprime.gylog.ui.exercises.add

import androidx.lifecycle.MutableLiveData
import com.gerosprime.gylog.base.FetchState
import com.gerosprime.gylog.models.exercises.ExerciseDatabaseSaveResult
import com.gerosprime.gylog.models.exercises.LoadedSingleExerciseResult
import com.gerosprime.gylog.models.muscle.MuscleEnum

interface ExerciseAddViewModel {
    val fetchStateMLD : MutableLiveData<FetchState>
    val loadResultMLD : MutableLiveData<LoadedSingleExerciseResult >
    val saveResultMLD : MutableLiveData<ExerciseDatabaseSaveResult>

    fun saveExercise(recordId : Long?,
                     name : String, description : String, direction : String,
                     muscles : ArrayList<MuscleEnum>)

    fun loadExercise(exerciseId: Long?)


}