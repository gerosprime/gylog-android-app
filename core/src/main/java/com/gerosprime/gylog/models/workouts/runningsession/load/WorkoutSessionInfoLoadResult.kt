package com.gerosprime.gylog.models.workouts.runningsession.load

import com.gerosprime.gylog.models.workouts.WorkoutEntity
import com.gerosprime.gylog.models.workouts.runningsession.WorkoutSessionEntity
import java.util.*

class WorkoutSessionInfoLoadResult(val workout : WorkoutEntity,
                                   val session : WorkoutSessionEntity,
                                   val started : Date,
                                   val ended : Date?,
                                   val setsPerformed : Int,
                                   val setsTotal : Int,
                                   val totalWeight : Float)
