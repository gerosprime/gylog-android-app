package com.gerosprime.gylog.models.programs.edit.load

import com.gerosprime.gylog.models.programs.ProgramEntity
import com.gerosprime.gylog.models.workouts.WorkoutEntity

class EditProgramSetToCacheResult (val programEntity: ProgramEntity,
                                   val workouts : ArrayList<WorkoutEntity>)