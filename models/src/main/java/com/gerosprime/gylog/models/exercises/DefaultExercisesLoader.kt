package com.gerosprime.gylog.models.exercises

import com.gerosprime.gylog.models.muscle.MuscleEnum
import com.gerosprime.gylog.models.states.ModelsCache
import io.reactivex.Completable
import io.reactivex.Single

class DefaultExercisesLoader(private val modelsCache: ModelsCache) : ExercisesLoader {


    private fun exercisesDatabase() : Single<List<ExerciseEntity>> {
        return Single.just(arrayListOf(
//            ExerciseEntity(1, "Inclined Bench Press (Barbell)", "Bla bla bla",
//                arrayListOf(MuscleEnum.CHEST, MuscleEnum.CHEST_LOWER, MuscleEnum.CHEST_UPPER)),
//            ExerciseEntity(2, "Inclined Bench Press (Dumbbell)", "Bla bla bla",
//                arrayListOf(MuscleEnum.CHEST, MuscleEnum.CHEST_LOWER, MuscleEnum.CHEST_UPPER)),
//            ExerciseEntity(3, "Bench Press (Barbell)", "Bla bla bla",
//                arrayListOf(MuscleEnum.CHEST, MuscleEnum.CHEST_LOWER, MuscleEnum.CHEST_UPPER)),
//            ExerciseEntity(4, "Bench Press (Dumbbell)", "Bla bla bla",
//                arrayListOf(MuscleEnum.CHEST, MuscleEnum.CHEST_LOWER, MuscleEnum.CHEST_UPPER)),
//            ExerciseEntity(5, "Bicep Curl (Barbell)", "Bla bla bla",
//                arrayListOf(MuscleEnum.BICEPS)),
//            ExerciseEntity(6, "Bicep Curl (Dumbbell)", "Bla bla bla",
//                arrayListOf(MuscleEnum.BICEPS)),
//            ExerciseEntity(7, "Strict Military Press", "Bla bla bla",
//                arrayListOf(MuscleEnum.SHOULDER_SIDE, MuscleEnum.SHOULDER_FRONT)),
//            ExerciseEntity(8, "Barbell Deadlift", "Bla bla bla",
//                arrayListOf(MuscleEnum.BACK_LOWER, MuscleEnum.QUADS, MuscleEnum.BACK_UPPER)))
        ))
    }

    override fun loadExercises(): Single<LoadedExercisesResult> {
        return exercisesDatabase()
            .flatMapCompletable { exercises -> fillCache(modelsCache, exercises) }
            .toSingleDefault(LoadedExercisesResult(modelsCache.exercisesList))
    }

    private fun fillCache(modelsCache: ModelsCache,
                           exercises : List<ExerciseEntity>) : Completable {
        return Completable.fromAction {

            modelsCache.exercisesList.clear()
            modelsCache.exercisesMap.clear()

            for (exercise in exercises) {
                modelsCache.exercisesList.add(exercise)
                modelsCache.exercisesMap[exercise.recordId] = exercise
            }

        }
    }
}