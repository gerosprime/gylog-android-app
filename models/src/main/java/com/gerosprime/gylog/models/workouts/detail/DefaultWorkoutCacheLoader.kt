package com.gerosprime.gylog.models.workouts.detail

import com.gerosprime.gylog.models.exercises.templates.ExerciseTemplateEntity
import com.gerosprime.gylog.models.states.ModelsCache
import io.reactivex.Single

class DefaultWorkoutCacheLoader(private val modelsCache: ModelsCache)
    : WorkoutCacheLoader {

    override fun load(workoutRecordId: Long): Single<LoadWorkoutFromCacheResult>
            = Single.fromCallable {


        val workout = modelsCache.workoutsMap[workoutRecordId]!!
        var duration = 0

        for (exercise in workout.exercises) {
            duration += computeExerciseDuration(exercise)
        }

        LoadWorkoutFromCacheResult(workout, workoutRecordId, duration)

    }

    private fun computeExerciseDuration(template: ExerciseTemplateEntity) : Int {
        var duration = 0
        for (template in template.setTemplates) {
            duration += template.restTimeSeconds
        }

        return duration
    }
}