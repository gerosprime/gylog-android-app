package com.gerosprime.gylog.ui.programs.add

import androidx.lifecycle.MutableLiveData
import com.gerosprime.gylog.models.exercises.ExerciseTemplatesAddToCacheResult
import com.gerosprime.gylog.models.programs.EditProgramSetToCacheResult
import com.gerosprime.gylog.models.workouts.WorkoutAddToCacheResult

interface ProgramsAddViewModel {

    val programSetToCacheResultMLD : MutableLiveData<EditProgramSetToCacheResult>
    val workoutAddToCacheResultMLD : MutableLiveData<WorkoutAddToCacheResult>

    fun saveProgramToDB(name : String, description : String)

    fun addWorkoutToCache(name : String?, description: String?)

    fun loadNewProgram()
    fun clearAll()

}