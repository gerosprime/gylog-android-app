package com.gerosprime.gylog.models.workouts.edit

import com.gerosprime.gylog.models.exercises.ExerciseEntity
import com.gerosprime.gylog.models.exercises.ExerciseTemplateEntity
import com.gerosprime.gylog.models.states.EditProgramEntityState
import io.reactivex.Single

class DefaultWorkoutSetExerciseCacheUC(private val state: EditProgramEntityState)
    : WorkoutSetExerciseCacheUC {

    override fun setNewExercises(
        workoutIndex: Int,
        selectedExercises: List<ExerciseEntity>
    ): Single<WorkoutExerciseSetCacheResult> {

        return Single.fromCallable {

            val workoutEntity = state.workouts?.get(workoutIndex)

            val newExercises : ArrayList<ExerciseTemplateEntity> = arrayListOf()
            for (selectedExercise in selectedExercises) {
                // workoutId will be set upon saving workout entity in database
                newExercises.add(
                    ExerciseTemplateEntity(name = selectedExercise.name,
                        exerciseId = selectedExercise.recordId,
                        setTemplates = arrayListOf())
                )
            }

            workoutEntity?.exercises = newExercises
            WorkoutExerciseSetCacheResult(workoutIndex)
        }


    }
}