package com.gerosprime.gylog.models.exercises

import com.gerosprime.gylog.models.states.ModelCacheBuilder
import com.gerosprime.gylog.models.states.ModelsCache
import io.reactivex.Maybe
import io.reactivex.Single

class DefaultExercisesCacheLoader(private val modelsCache: ModelsCache,
                                  private val cacheBuilder: ModelCacheBuilder)
    : ExercisesCacheLoader {


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

        return cacheBuilder.build().andThen(Single.fromCallable {
            LoadedExercisesResult(modelsCache.exercisesList)
        })

    }

    override fun loadExercise(recordId: Long?): Maybe<LoadedSingleExerciseResult> {
        return Maybe.fromCallable {

            if (modelsCache.exercisesMap.containsKey(recordId)) {
                val exercise = modelsCache.exercisesMap[recordId] as ExerciseEntity
                LoadedSingleExerciseResult(
                    recordId, exercise.name,
                    exercise.description, exercise.instruction,
                    exercise.targetMuscles
                )
            } else {
                LoadedSingleExerciseResult(
                    null, "",
                    "", "", arrayListOf()
                )
            }

        }
    }
}