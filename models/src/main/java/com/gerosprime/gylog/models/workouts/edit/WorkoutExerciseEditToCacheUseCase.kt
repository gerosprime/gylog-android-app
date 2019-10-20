package com.gerosprime.gylog.models.workouts.edit

import com.gerosprime.gylog.models.exercises.ExerciseEntity
import io.reactivex.Single

interface WorkoutExerciseEditToCacheUseCase {
    fun edit(selectedExercises : List<ExerciseEntity>)
            : Single<WorkoutExerciseSetCacheResult>
}