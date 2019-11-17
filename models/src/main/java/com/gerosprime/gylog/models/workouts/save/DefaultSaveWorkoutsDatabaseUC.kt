package com.gerosprime.gylog.models.workouts.save

import com.gerosprime.gylog.models.database.GylogEntityDatabase
import com.gerosprime.gylog.models.states.ModelCacheBuilder
import com.gerosprime.gylog.models.states.ModelsCache
import com.gerosprime.gylog.models.workouts.WorkoutEntity
import io.reactivex.Single

class DefaultSaveWorkoutsDatabaseUC(private val modelsCache: ModelsCache,
                                    private val cacheBuilder: ModelCacheBuilder,
                                    private val database: GylogEntityDatabase
) : SaveWorkoutsDatabaseUC {

    override fun save(programId: Long, workouts: ArrayList<WorkoutEntity>):
            Single<SaveWorkoutsDatabaseResult> {

        val workoutDao = database.workoutEntityDao()

        return cacheBuilder.build().andThen(workoutDao.saveWorkouts(workouts)
            .flatMapSingle { workoutIds -> updateCache(workoutIds, workouts) })
    }

    private fun updateCache(ids : List<Long>, workouts: ArrayList<WorkoutEntity>)
            : Single<SaveWorkoutsDatabaseResult> {
        return Single.fromCallable {


            for (index in 0..ids.size) {
                val workout = workouts[index]
                workout.recordId = ids[index]
                modelsCache.workoutsMap[workout.recordId as Long] = workout
            }

            modelsCache.workouts.addAll(workouts)

            SaveWorkoutsDatabaseResult(workouts = workouts)

        }
    }

}