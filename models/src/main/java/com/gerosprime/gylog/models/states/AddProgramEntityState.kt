package com.gerosprime.gylog.models.states

import com.gerosprime.gylog.models.programs.ProgramEntity
import com.gerosprime.gylog.models.workouts.WorkoutEntity

class AddProgramEntityState (var newProgram : ProgramEntity?,
                             var workouts : ArrayList<WorkoutEntity>?)