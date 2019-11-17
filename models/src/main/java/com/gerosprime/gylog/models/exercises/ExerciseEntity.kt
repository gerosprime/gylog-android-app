package com.gerosprime.gylog.models.exercises

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.gerosprime.gylog.models.muscle.MuscleEnum

@Entity
data class ExerciseEntity(@PrimaryKey val recordId : Long,
                          val name : String, val description : String) {
    @Ignore var targetMuscles : ArrayList<MuscleEnum> = arrayListOf()
}