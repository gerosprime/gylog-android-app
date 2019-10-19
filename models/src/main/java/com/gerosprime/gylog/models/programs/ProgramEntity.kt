package com.gerosprime.gylog.models.programs

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.gerosprime.gylog.models.workouts.WorkoutEntity

@Entity
data class ProgramEntity (@PrimaryKey val recordId: Long? = null,
               var name : String? = null, var description : String? = null,
               var workouts : ArrayList<WorkoutEntity>? = null)