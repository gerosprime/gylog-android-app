package com.gerosprime.gylog.models.workouts

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.gerosprime.gylog.models.exercises.ExerciseTemplateEntity

@Entity
data class WorkoutEntity(@PrimaryKey val recordId: Long? = null,
                         val name:String? = null,
                         val description:String? = null,
                         val programId : Long? = null,
                         var day : Int? = null,
                         var exercises : List<ExerciseTemplateEntity>? = null,
                         var totalTimeSeconds : Int? = null)