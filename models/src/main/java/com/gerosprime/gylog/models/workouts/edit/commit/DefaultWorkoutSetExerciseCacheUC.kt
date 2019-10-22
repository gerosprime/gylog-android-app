package com.gerosprime.gylog.models.workouts.edit.commit

import com.gerosprime.gylog.models.exercises.ExerciseEntity
import com.gerosprime.gylog.models.exercises.templates.ExerciseTemplateEntity
import com.gerosprime.gylog.models.states.EditProgramEntityCache
import io.reactivex.Single

class DefaultWorkoutSetExerciseCacheUC(private val cache: EditProgramEntityCache)
    : WorkoutSetExerciseCacheUC {

    override fun setNewExercises(
        workoutIndex: Int,
        selectedExercises: List<ExerciseEntity>
    ): Single<WorkoutExerciseSetCacheResult> {

        return Single.fromCallable {

            val workoutEntity = cache.editWorkouts[workoutIndex]



            val newExercises : ArrayList<ExerciseTemplateEntity> = arrayListOf()
            for (selectedExercise in selectedExercises) {
                // workoutId will be set upon saving workout entity in database

                if (cache.editExercisesTemplateMap.containsKey(selectedExercise.recordId)) {
                    newExercises.add(
                        cache.editExercisesTemplateMap[selectedExercise.recordId]
                                as ExerciseTemplateEntity)
                } else {
                    newExercises.add(
                        ExerciseTemplateEntity(
                            name = selectedExercise.name,
                            exerciseId = selectedExercise.recordId,
                            setTemplates = arrayListOf()
                        )
                    )
                }


            }

            workoutEntity.exercises = newExercises
            WorkoutExerciseSetCacheResult(
                workoutIndex
            )
        }


    }
}