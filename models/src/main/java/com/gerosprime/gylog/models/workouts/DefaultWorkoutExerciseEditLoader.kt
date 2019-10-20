package com.gerosprime.gylog.models.workouts

import com.gerosprime.gylog.models.exercises.ExerciseEntity
import com.gerosprime.gylog.models.exercises.ExercisesLoader
import com.gerosprime.gylog.models.states.EditProgramEntityState
import com.gerosprime.gylog.models.states.ModelsCache
import com.gerosprime.gylog.models.workouts.edit.WorkoutExerciseEditLoadResult
import com.gerosprime.gylog.models.workouts.edit.WorkoutExerciseEditLoader
import io.reactivex.Completable
import io.reactivex.Single


class DefaultWorkoutExerciseEditLoader(private val editProgramEntityState: EditProgramEntityState,
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

            val workoutExercises : MutableMap<Long, ExerciseEntity> = mutableMapOf()
            val workout = editProgramEntityState.workouts!![workoutIndex]

            for (template in workout.exercises!!) {

                val id = template.exerciseId as Long
                workoutExercises[id] = exercisesMap[id]!!
            }

            WorkoutExerciseEditLoadResult(workoutExercises, exercisesList)
        })


    }


}