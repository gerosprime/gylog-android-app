package com.gerosprime.gylog.models.states

import com.gerosprime.gylog.models.programs.ProgramEntity
import com.gerosprime.gylog.models.workouts.WorkoutEntity

class EditProgramEntityState (var newProgram : ProgramEntity?,
                              var workouts : List<WorkoutEntity>?)