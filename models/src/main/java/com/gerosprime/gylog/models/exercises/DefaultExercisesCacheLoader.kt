package com.gerosprime.gylog.models.exercises

import com.gerosprime.gylog.models.states.ModelCacheBuilder
import com.gerosprime.gylog.models.states.ModelsCache
import io.reactivex.Maybe
import io.reactivex.Single

class DefaultExercisesCacheLoader(private val modelsCache: ModelsCache,
                                  private val cacheBuilder: ModelCacheBuilder)
    : ExercisesCacheLoader {

    override fun loadExercises(): Single<LoadedExercisesResult> {

        return cacheBuilder.build().andThen(Single.fromCallable {
            LoadedExercisesResult(modelsCache.exercisesList)
        })

    }

    override fun loadExercise(recordId: Long?): Maybe<LoadedSingleExerciseResult> {
        return cacheBuilder.build().andThen(Maybe.fromCallable {

            if (modelsCache.exercisesMap.containsKey(recordId)) {
                val exercise = modelsCache.exercisesMap[recordId] as ExerciseEntity
                LoadedSingleExerciseResult(
                    recordId, exercise.name,
                    exercise.description, exercise.instruction,
                    exercise.targetMuscles
                )
            } else {
                LoadedSingleExerciseResult(
                    null, "",
                    "", "", arrayListOf()
                )
            }

        })
    }
}