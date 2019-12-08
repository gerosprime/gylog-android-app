package com.gerosprime.gylog.models.workouts.edit.load

import com.gerosprime.gylog.models.muscle.MuscleEnum
import io.reactivex.Single


interface WorkoutExerciseEditLoader {
    fun loadWorkoutExercises(workoutIndex : Int,
                             targetMuscles : ArrayList<MuscleEnum> = arrayListOf()) :
            Single<WorkoutExerciseEditLoadResult>
}