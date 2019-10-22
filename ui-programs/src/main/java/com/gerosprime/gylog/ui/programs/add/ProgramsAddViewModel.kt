package com.gerosprime.gylog.ui.programs.add

import androidx.lifecycle.MutableLiveData
import com.gerosprime.gylog.models.programs.edit.load.EditProgramSetToCacheResult
import com.gerosprime.gylog.models.workouts.edit.add.WorkoutAddToCacheResult

interface ProgramsAddViewModel {

    val programSetToCacheResultMLD : MutableLiveData<EditProgramSetToCacheResult>
    val workoutAddToCacheResultMLD : MutableLiveData<WorkoutAddToCacheResult>

    fun saveProgramToDB(name : String, description : String)

    fun addWorkoutToCache(name : String?, description: String?)

    fun loadNewProgram()
    fun clearAll()

}