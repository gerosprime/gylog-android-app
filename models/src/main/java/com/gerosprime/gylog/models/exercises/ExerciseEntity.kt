package com.gerosprime.gylog.models.exercises

import com.gerosprime.gylog.models.muscle.MuscleEnum

data class ExerciseEntity(val recordId : Long,
                          val name : String, val description : String,
                          val targetMuscles : List<MuscleEnum>)