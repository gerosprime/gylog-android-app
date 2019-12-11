package com.gerosprime.gylog.models.exercises

import com.gerosprime.gylog.models.database.GylogEntityDatabase
import com.gerosprime.gylog.models.states.ModelCacheBuilder
import com.gerosprime.gylog.models.states.ModelsCache
import io.reactivex.Single

class DefaultExerciseDatabaseSaver(private val database : GylogEntityDatabase,
                                   private val modelsCache: ModelsCache,
                                   private val cacheBuilder: ModelCacheBuilder)
    : ExerciseDatabaseSaver {

    override fun save(exercise: ExerciseEntity): Single<ExerciseDatabaseSaveResult> {
        return cacheBuilder.build().andThen(Single.fromCallable {


            val recordId = database.exerciseEntityDao().saveExercise(exercise)
            exercise.recordId = recordId


            var index = if (modelsCache.exercisesMap.containsKey(recordId))
                    modelsCache.exercisesList.indexOf(modelsCache.exercisesMap[recordId])
                else modelsCache.exercisesList.size
            val flag = if (modelsCache.exercisesMap.containsKey(recordId)) {

                modelsCache.exercisesList[index] = exercise

                ExerciseDatabaseSaveResult.Flag.UPDATED
            } else {
                modelsCache.exercisesList.add(exercise)
                ExerciseDatabaseSaveResult.Flag.NEW
            }

            modelsCache.exercisesMap[recordId] = exercise

            ExerciseDatabaseSaveResult(exercise, flag, index)
        })
    }
}