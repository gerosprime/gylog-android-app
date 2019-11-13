package com.gerosprime.gylog.models.workouts.runningsession.create

import com.gerosprime.gylog.models.exercises.performed.ExercisePerformedEntity
import com.gerosprime.gylog.models.exercises.performedsets.PerformedSetEntity
import com.gerosprime.gylog.models.states.ModelsCache
import com.gerosprime.gylog.models.states.RunningWorkoutSessionCache
import com.gerosprime.gylog.models.workouts.runningsession.WorkoutSessionEntity
import io.reactivex.Single
import java.util.*
import kotlin.collections.ArrayList

class DefaultWorkoutSessionCreator(private val modelsCache: ModelsCache,
                                   private val runningSessionCache: RunningWorkoutSessionCache)
    : WorkoutSessionCreator {

    override fun create(workoutEntityId: Long):
            Single<WorkoutSessionCreationResult> = Single.fromCallable {


        val prePerformedExercises : ArrayList<ExercisePerformedEntity> = arrayListOf()
        val duration = 0L

        // Retrieve existing workout to create exercises from templates
        val workoutEntity = modelsCache.workoutsMap[workoutEntityId]
        for (exercise in workoutEntity!!.exercises) {

            //
            val performedSets : ArrayList<PerformedSetEntity> = arrayListOf()

            // Fill up performed set from template
            for (template in exercise.setTemplates) {

                performedSets.add(PerformedSetEntity(
                    recordId = null,
                    reps = null,
                    weight = null,
                    counterWeight = 0f,
                    durationSeconds = 0,
                    restTimeSeconds = template.restTimeSeconds,
                    upToFailure = template.upToFailure,
                    templateSetId = template.recordId,
                    initialReps  = template.minReps,
                    initialWeight = template.weight,
                    previousPerformedSetId = null,
                    previousReps = null,
                    previousWeight = null,
                    datePerformed = null))

            }

            prePerformedExercises.add(ExercisePerformedEntity(
                exerciseId = exercise.exerciseId,
                previousExercisePerformedId = 0,
                performedSets = performedSets, name = exercise.name))
        }

        val workoutSession
                = WorkoutSessionEntity(
            workoutId = workoutEntityId,
            exercisesPerformed = prePerformedExercises,
            durationSeconds = duration,
            startDate = Date()
        )

        runningSessionCache.prePerformedExercises = prePerformedExercises
        runningSessionCache.workoutSessionEntity = workoutSession

        WorkoutSessionCreationResult(workoutEntityId, workoutSession,
            prePerformedExercises)
    }
}