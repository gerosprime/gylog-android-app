package com.gerosprime.gylog.models.workouts.history

import com.gerosprime.gylog.models.exercises.performed.ExercisePerformedEntity
import com.gerosprime.gylog.models.exercises.performedsets.PerformedSetEntity
import com.gerosprime.gylog.models.states.ModelCacheBuilder
import com.gerosprime.gylog.models.states.ModelsCache
import io.reactivex.Single
import java.util.*
import kotlin.Comparator
import kotlin.collections.ArrayList

class DefaultWorkoutExerciseHistoryLoader(private val cacheBuilder: ModelCacheBuilder,
                                          private val modelsCache: ModelsCache) : WorkoutExerciseHistoryLoader {

    override fun load(workoutId: Long, exerciseId: Long): Single<WorkoutExerciseHistoryResult> {
        return cacheBuilder.build().andThen(Single.fromCallable {

            val exercises : ArrayList<ExercisePerformedEntity> = arrayListOf()
            for (performedExercise in modelsCache.performedExercises) {
                if (performedExercise.exerciseId == exerciseId
                    && performedExercise.workoutId == workoutId) {
                    exercises.add(performedExercise)

                    val performedSets : ArrayList<PerformedSetEntity> = arrayListOf()
                    for (performedSet in modelsCache.performedSets) {
                        if (performedSet.exercisePerformedId == performedExercise.recordId) {
                            performedSets.add(performedSet)
                        }
                    }

                    performedExercise.performedSets = performedSets
                }
            }

            exercises.sortWith(Comparator { p0, p1 ->
                p1.performedDate?.compareTo(p0.performedDate)!!
            })

            WorkoutExerciseHistoryResult(workoutId, exerciseId,
                modelsCache.exercisesMap[exerciseId]!!, exercises)
        })
    }
}