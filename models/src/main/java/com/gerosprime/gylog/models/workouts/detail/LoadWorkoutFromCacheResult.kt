package com.gerosprime.gylog.models.workouts.detail

import com.gerosprime.gylog.models.workouts.WorkoutEntity

class LoadWorkoutFromCacheResult (val workout: WorkoutEntity,
                                  val workoutRecordId : Long,
                                  val durationSeconds : Int)