package com.gerosprime.gylog.models.exercises

import androidx.room.PrimaryKey

data class ExercisePerformedEntity(@PrimaryKey val recordId : Long,
                                   val exerciseId : Long,
                                   val previousExercisePerformedId : Long,
                                   val performedSets : List<PerformedSetEntity>)