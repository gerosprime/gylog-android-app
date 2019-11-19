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

            var index = modelsCache.exercisesList.size
            val flag = if (modelsCache.exercisesMap.containsKey(recordId)) {

                for (i in 0 until modelsCache.exercisesList.size) {
                    if (modelsCache.exercisesList[i].recordId
                        == exercise.recordId) {
                        index = i
                        modelsCache.exercisesList[index] = exercise
                        break
                    }
                }
                ExerciseDatabaseSaveResult.Flag.UPDATED
            } else {
                modelsCache.exercisesList.add(exercise)
                modelsCache.exercisesMap[recordId] = exercise
                ExerciseDatabaseSaveResult.Flag.NEW
            }

            ExerciseDatabaseSaveResult(exercise, flag, index)
        })
    }
}