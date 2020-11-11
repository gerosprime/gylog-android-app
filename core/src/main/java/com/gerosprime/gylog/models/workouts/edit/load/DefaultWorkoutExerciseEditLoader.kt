package com.gerosprime.gylog.models.workouts.edit.load

import com.gerosprime.gylog.models.exercises.ExerciseEntity
import com.gerosprime.gylog.models.exercises.templates.ExerciseTemplateEntity
import com.gerosprime.gylog.models.muscle.MuscleEnum
import com.gerosprime.gylog.models.states.EditProgramEntityCache
import com.gerosprime.gylog.models.states.ModelCacheBuilder
import com.gerosprime.gylog.models.states.ModelsCache
import io.reactivex.Single


class DefaultWorkoutExerciseEditLoader(private val editProgramEntityCache: EditProgramEntityCache,
                                       private val modelsCache: ModelsCache,
                                       private val cacheBuilder: ModelCacheBuilder
)
    : WorkoutExerciseEditLoader {

    override fun loadWorkoutExercises(workoutIndex : Int,
                                      targetMuscles : ArrayList<MuscleEnum>): Single<WorkoutExerciseEditLoadResult> {

        return cacheBuilder.build().andThen(Single.fromCallable {

            val exercisesList = modelsCache.exercisesList

            val exercisesListFiltered : ArrayList<ExerciseEntity> = arrayListOf()
            for (exerciseEntity in exercisesList) {
                for (targetMuscle in targetMuscles) {
                    if (exerciseEntity.targetMuscles.contains(targetMuscle)) {
                        exercisesListFiltered.add(exerciseEntity)
                        break
                    }
                }
            }

            val exercisesMap = modelsCache.exercisesMap

            val workoutExercises = editProgramEntityCache.editWorkouts[workoutIndex].exercises

            val exercisesMapCache : MutableMap<Long, ExerciseTemplateEntity> = mutableMapOf()
            val exercisesCache : ArrayList<ExerciseTemplateEntity> = arrayListOf()

            for (exercise in workoutExercises) {
                exercisesCache.add(exercise)
                exercisesMapCache[exercise.exerciseId] = exercise
            }

            // Set to cache
            editProgramEntityCache.editExercisesTemplate = exercisesCache
            editProgramEntityCache.editExercisesTemplateMap = exercisesMapCache

            val copyWorkoutExercisesMap : MutableMap<Long, ExerciseEntity> = mutableMapOf()

            for (template in exercisesCache) {

                val id = template.exerciseId
                copyWorkoutExercisesMap[id] = exercisesMap[id]!!
            }

            WorkoutExerciseEditLoadResult(
                copyWorkoutExercisesMap,
                if (targetMuscles.isEmpty()) exercisesList else exercisesListFiltered
            )
        })


    }


}