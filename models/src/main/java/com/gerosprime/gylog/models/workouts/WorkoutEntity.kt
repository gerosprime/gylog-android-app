package com.gerosprime.gylog.models.workouts

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.gerosprime.gylog.models.exercises.ExerciseEntity

@Entity
data class WorkoutEntity(@PrimaryKey val recordId: Long,
                         val name:String, val description:String,
                         val programId : Long,
                         val day : Int,
                         val exercises : List<ExerciseEntity>)