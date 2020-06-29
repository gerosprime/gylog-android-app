package com.gerosprime.gylog.ui.exercises.add

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.gerosprime.gylog.base.FetchState
import com.gerosprime.gylog.models.exercises.ExerciseDatabaseSaveResult
import com.gerosprime.gylog.models.exercises.LoadedSingleExerciseResult
import com.gerosprime.gylog.models.muscle.MuscleEnum

interface ExerciseAddViewModel {

    val fetchStateLiveData : LiveData<FetchState>
    val loadResultLiveData : LiveData<LoadedSingleExerciseResult>
    val saveResultLiveData : LiveData<ExerciseDatabaseSaveResult>

    fun saveExercise(recordId : Long?,
                     name : String, description : String, direction : String,
                     muscles : ArrayList<MuscleEnum>)

    fun loadExercise(exerciseId: Long?)


}