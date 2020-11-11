package com.gerosprime.gylog.models.programs.edit.load

import com.gerosprime.gylog.models.programs.ProgramEntity
import com.gerosprime.gylog.models.workouts.WorkoutEntity

class EditProgramSetToCacheResult (val programEntity: ProgramEntity,
                                   val workouts : ArrayList<WorkoutEntity>,
                                   val mode : Int) {

    object Mode {
        const val ADD = 0
        const val EDIT = 1
    }

}