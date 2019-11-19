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
                    val existingExercise = cache.editExercisesTemplateMap[selectedExercise.recordId]!!
                    newExercises.add(existingExercise)
                    cache.editExercisesTemplateMap.remove(selectedExercise.recordId)
                } else {

                    val exerciseTemplate = ExerciseTemplateEntity(
                        name = selectedExercise.name,
                        exerciseId = selectedExercise.recordId!!
                    )
                    exerciseTemplate.setTemplates = arrayListOf()

                    newExercises.add(exerciseTemplate)
                }


            }

            workoutEntity.exercises = newExercises
            workoutEntity.deleteExercises = arrayListOf()
            workoutEntity.deleteExercises.addAll(cache.editExercisesTemplateMap.values)

            WorkoutExerciseSetCacheResult(
                workoutIndex
            )
        }


    }
}