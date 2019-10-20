package com.gerosprime.gylog.models.programs

import com.gerosprime.gylog.models.workouts.WorkoutEntity

class EditProgramSetToCacheResult (val programEntity: ProgramEntity,
                                   val workouts : ArrayList<WorkoutEntity>)