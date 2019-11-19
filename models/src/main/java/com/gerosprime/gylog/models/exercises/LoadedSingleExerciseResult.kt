package com.gerosprime.gylog.models.exercises

import com.gerosprime.gylog.models.muscle.MuscleEnum

class LoadedSingleExerciseResult(
    val recordId: Long?,
    val name: String,
    val description: String,
    val directions: String,
    val muscles: ArrayList<MuscleEnum>
)
