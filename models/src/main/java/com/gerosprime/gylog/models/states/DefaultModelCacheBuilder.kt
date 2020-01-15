package com.gerosprime.gylog.models.states

import com.gerosprime.gylog.models.database.GylogEntityDatabase
import io.reactivex.Completable
import java.util.concurrent.atomic.AtomicBoolean

class DefaultModelCacheBuilder (private val modelsCache: ModelsCache,
                                private val database: GylogEntityDatabase
) : ModelCacheBuilder {

    private val buildingIndicator = AtomicBoolean(false)

    override fun build(): Completable {

        if (buildingIndicator.get()) return Completable.complete()

        return Completable.fromAction {

            buildingIndicator.set(true)
            if (modelsCache.programsMap.isEmpty()) {

                val programs = database.programEntityDao().getPrograms()

                for (program in programs) {
                    modelsCache.programsMap[program.recordId as Long] = program
                    modelsCache.programs.add(program)
                }

                val workouts = database.workoutEntityDao().loadWorkouts()
                for (workout in workouts) {
                    modelsCache.workoutsMap[workout.recordId as Long] = workout
                    modelsCache.workouts.add(workout)

                    val program = modelsCache.programsMap[workout.programId]
                    program?.workouts?.add(workout)

                }

                val exerciseTemplates = database.exerciseTemplateEntityDao().loadExercises()
                for (exercise in exerciseTemplates) {
                    modelsCache.templateExercisesMap[exercise.recordId as Long] = exercise
                    modelsCache.templateExercises.add(exercise)

                    val workout = modelsCache.workoutsMap[exercise.workoutId]
                    if (!exercise.markDeletedOnRecord)
                        workout?.exercises?.add(exercise)

                }

                val loadTemplateSets = database.templateSetEntityDao().loadTemplateSets()
                for (loadTemplateSet in loadTemplateSets) {
                    val exerciseEntity =
                        modelsCache.templateExercisesMap[loadTemplateSet.exerciseTemplateId]

                    if (!loadTemplateSet.markDeleteOnRecord)
                        exerciseEntity?.setTemplates?.add(loadTemplateSet)
                }

                // Merging to programs

            }

            if (modelsCache.exercisesMap.isEmpty()) {
                val exercises = database.exerciseEntityDao().loadExercises()
                for (exercise in exercises) {
                    modelsCache.exercisesMap[exercise.recordId!!] = exercise
                    modelsCache.exercisesList.add(exercise)
                }
            }

            if (modelsCache.performedExercises.isEmpty()) {
                val performedExercises = database.exercisePerformedDao().loadExercises()

                for (performedExercise in performedExercises) {
                    modelsCache.performedExercises[performedExercise.recordId!!] = performedExercise
                }
            }

            if (modelsCache.performedSets.isEmpty()) {
                val performedSets = database.performedSetEntityDao().loadPerformedSets()

                for (performedSet in performedSets) {
                    modelsCache.performedSets[performedSet.recordId!!] = performedSet
                }
            }

            if (modelsCache.bodyWeightMap.isEmpty()) {
                val bodyWeights = database.bodyWeightEntityDao().loadBodyWeights()

                for (bodyWeight in bodyWeights) {
                    modelsCache.bodyWeightMap[bodyWeight.recordId!!] = bodyWeight
                }
            }


            if (modelsCache.bodyFatMap.isEmpty()) {
                val bodyFats = database.bodyFatEntityDao().loadBodyFats()

                for (bodyFat in bodyFats) {
                    modelsCache.bodyFatMap[bodyFat.recordId!!] = bodyFat
                }
            }

            buildingIndicator.set(false)
        }

    }

}