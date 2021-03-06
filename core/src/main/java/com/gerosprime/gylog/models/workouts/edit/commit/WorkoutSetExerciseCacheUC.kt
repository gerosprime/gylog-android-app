package com.gerosprime.gylog.models.workouts.edit.commit

import com.gerosprime.gylog.models.exercises.ExerciseEntity
import io.reactivex.Single


interface WorkoutSetExerciseCacheUC {
    fun setNewExercises(workoutIndex : Int,
                        selectedExercises : List<ExerciseEntity>)
            : Single<WorkoutExerciseSetCacheResult>
}