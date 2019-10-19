package com.gerosprime.gylog.ui.programs.add

import androidx.lifecycle.MutableLiveData
import com.gerosprime.gylog.models.exercises.ExerciseTemplatesAddToCacheResult
import com.gerosprime.gylog.models.programs.NewProgramSetToCacheResult
import com.gerosprime.gylog.models.workouts.WorkoutAddToCacheResult
import com.gerosprime.gylog.models.workouts.WorkoutEntity

interface ProgramsAddViewModel {

    val programSetToCacheResultMLD : MutableLiveData<NewProgramSetToCacheResult>
    val workoutAddToCacheResultMLD : MutableLiveData<WorkoutAddToCacheResult>
    val exerciseTemplateAddResultMTD : MutableLiveData<ExerciseTemplatesAddToCacheResult>

    fun saveProgramToDB(name : String, description : String)

    fun addWorkoutToCache(name : String?, description: String?)

    fun loadNewProgram()
    fun clearAll()

}