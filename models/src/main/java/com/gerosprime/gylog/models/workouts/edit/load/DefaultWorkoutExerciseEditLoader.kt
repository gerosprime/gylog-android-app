package com.gerosprime.gylog.models.workouts.edit.load

import com.gerosprime.gylog.models.exercises.ExerciseEntity
import com.gerosprime.gylog.models.exercises.templates.ExerciseTemplateEntity
import com.gerosprime.gylog.models.exercises.ExercisesLoader
import com.gerosprime.gylog.models.states.EditProgramEntityCache
import com.gerosprime.gylog.models.states.ModelsCache
import io.reactivex.Completable
import io.reactivex.Single


class DefaultWorkoutExerciseEditLoader(private val editProgramEntityCache: EditProgramEntityCache,
                                       private val modelsCache: ModelsCache,
                                       private val exercisesLoader: ExercisesLoader)
    : WorkoutExerciseEditLoader {

    override fun loadWorkoutExercises(workoutIndex : Int): Single<WorkoutExerciseEditLoadResult> {

        var initCache = Completable.complete()

        // Fill an empty exercises cache if empty/null
        if (modelsCache.exercisesList.isNullOrEmpty() &&
                modelsCache.exercisesMap.isNullOrEmpty()) {
            initCache = Completable.fromSingle(exercisesLoader.loadExercises())

        }

        return initCache.andThen(Single.fromCallable {

            val exercisesList = modelsCache.exercisesList
            val exercisesMap = modelsCache.exercisesMap

            val workoutExercises = editProgramEntityCache.editWorkouts[workoutIndex].exercises

            val exercisesMapCache : MutableMap<Long, ExerciseTemplateEntity> = mutableMapOf()
            val exercisesCache : ArrayList<ExerciseTemplateEntity> = arrayListOf()

            for (exercise in workoutExercises) {
                exercisesCache.add(exercise)
                exercisesMapCache[exercise.exerciseId as Long] = exercise
            }

            // Set to cache
            editProgramEntityCache.editExercisesTemplate = exercisesCache
            editProgramEntityCache.editExercisesTemplateMap = exercisesMapCache

            val copyWorkoutExercisesMap : MutableMap<Long, ExerciseEntity> = mutableMapOf()

            for (template in exercisesCache) {

                val id = template.exerciseId as Long
                copyWorkoutExercisesMap[id] = exercisesMap[id]!!
            }

            WorkoutExerciseEditLoadResult(
                copyWorkoutExercisesMap,
                exercisesList
            )
        })


    }


}