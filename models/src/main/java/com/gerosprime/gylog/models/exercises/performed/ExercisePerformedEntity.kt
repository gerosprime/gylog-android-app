package com.gerosprime.gylog.models.exercises.performed

import androidx.room.PrimaryKey
import com.gerosprime.gylog.models.exercises.performedsets.PerformedSetEntity

data class ExercisePerformedEntity(@PrimaryKey val recordId : Long? = null,
                                   val exerciseId : Long,
                                   val previousExercisePerformedId : Long,
                                   val performedSets : ArrayList<PerformedSetEntity>,
                                   val name : String)