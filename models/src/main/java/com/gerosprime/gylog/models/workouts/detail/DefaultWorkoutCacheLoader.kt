package com.gerosprime.gylog.models.workouts.detail

import com.gerosprime.gylog.models.states.ModelCacheBuilder
import com.gerosprime.gylog.models.exercises.templates.ExerciseTemplateEntity
import com.gerosprime.gylog.models.states.ModelsCache
import io.reactivex.Single

class DefaultWorkoutCacheLoader(private val modelsCache: ModelsCache,
                                private val cacheBuilder: ModelCacheBuilder
)
    : WorkoutCacheLoader {

    override fun load(workoutRecordId: Long): Single<LoadWorkoutFromCacheResult>
            = cacheBuilder.build().andThen(Single.fromCallable {


        val workout = modelsCache.workoutsMap[workoutRecordId]!!
        var duration = 0

        for (exercise in workout.exercises) {
            duration += computeExerciseDuration(exercise)
        }

        LoadWorkoutFromCacheResult(workout, workoutRecordId, duration)

    })

    private fun computeExerciseDuration(template: ExerciseTemplateEntity) : Int {
        var duration = 0
        for (templateSet in template.setTemplates) {
            duration += templateSet.restTimeSeconds
        }

        return duration
    }
}