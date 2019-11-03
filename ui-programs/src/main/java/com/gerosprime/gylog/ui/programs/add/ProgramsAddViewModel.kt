package com.gerosprime.gylog.ui.programs.add

import androidx.lifecycle.MutableLiveData
import com.gerosprime.gylog.models.programs.edit.load.EditProgramSetToCacheResult
import com.gerosprime.gylog.models.programs.save.SaveProgramDatabaseResult
import com.gerosprime.gylog.models.workouts.edit.add.WorkoutAddToCacheResult

interface ProgramsAddViewModel {

    val programSetToCacheResultMLD : MutableLiveData<EditProgramSetToCacheResult>
    val workoutAddToCacheResultMLD : MutableLiveData<WorkoutAddToCacheResult>
    val saveProgramResultMLD : MutableLiveData<SaveProgramDatabaseResult>

    fun saveProgramToDB(name : String, description : String)

    fun addWorkoutToCache(name : String?, description: String?)

    fun loadProgramForEdit(programRecordId : Long)
    fun clearAll()

}