package com.gerosprime.gylog.models.states

import com.gerosprime.gylog.models.database.GylogEntityDatabase
import io.reactivex.Completable

class DefaultModelCacheBuilder (private val modelsCache: ModelsCache,
                                private val database: GylogEntityDatabase
) : ModelCacheBuilder {

    override fun build(): Completable {

        return Completable.fromAction {
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

        }

    }

}