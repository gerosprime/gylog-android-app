package com.gerosprime.gylog.ui.programs.add

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.gerosprime.gylog.models.programs.edit.load.EditProgramSetToCacheResult
import com.gerosprime.gylog.models.programs.save.SaveProgramDatabaseResult
import com.gerosprime.gylog.models.workouts.edit.add.WorkoutAddToCacheResult

interface ProgramsAddViewModel {

    val programSetToCacheResultMLD : LiveData<EditProgramSetToCacheResult>
    val workoutAddToCacheResultMLD : LiveData<WorkoutAddToCacheResult>
    val saveProgramResultMLD : LiveData<SaveProgramDatabaseResult>

    fun saveProgramToDB(name : String, description : String, imageUri : String)

    fun addWorkoutToCache(name : String?, description: String?)

    fun loadProgramForEdit(programRecordId : Long)
    fun clearAll()

}