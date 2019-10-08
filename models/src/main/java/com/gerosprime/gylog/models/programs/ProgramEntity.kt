package com.gerosprime.gylog.models.programs

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.gerosprime.gylog.models.workouts.WorkoutEntity

@Entity
data class ProgramEntity (@PrimaryKey val recordId: Long,
               val name : String, val description : String,
               val workouts : List<WorkoutEntity>)