package com.gerosprime.gylog.models.exercises

import com.gerosprime.gylog.models.muscle.MuscleEnum
import io.reactivex.Single

class DefaultExercisesLoader : ExercisesLoader {

    override fun loadExercises(): Single<LoadedExercisesResult> {
        var exercises = arrayListOf(
            ExerciseEntity(0, "Inclined Bench Press (Barbell)", "Bla bla bla",
                arrayListOf(MuscleEnum.CHEST, MuscleEnum.CHEST_LOWER, MuscleEnum.CHEST_UPPER)),
            ExerciseEntity(0, "Inclined Bench Press (Barbell)", "Bla bla bla",
                arrayListOf(MuscleEnum.CHEST, MuscleEnum.CHEST_LOWER, MuscleEnum.CHEST_UPPER)),
            ExerciseEntity(0, "Inclined Bench Press (Barbell)", "Bla bla bla",
                arrayListOf(MuscleEnum.CHEST, MuscleEnum.CHEST_LOWER, MuscleEnum.CHEST_UPPER)),
            ExerciseEntity(0, "Inclined Bench Press (Barbell)", "Bla bla bla",
                arrayListOf(MuscleEnum.CHEST, MuscleEnum.CHEST_LOWER, MuscleEnum.CHEST_UPPER)),
            ExerciseEntity(0, "Inclined Bench Press (Barbell)", "Bla bla bla",
                arrayListOf(MuscleEnum.CHEST, MuscleEnum.CHEST_LOWER, MuscleEnum.CHEST_UPPER)),
            ExerciseEntity(0, "Inclined Bench Press (Barbell)", "Bla bla bla",
                arrayListOf(MuscleEnum.CHEST, MuscleEnum.CHEST_LOWER, MuscleEnum.CHEST_UPPER)),
            ExerciseEntity(0, "Inclined Bench Press (Barbell)", "Bla bla bla",
                arrayListOf(MuscleEnum.CHEST, MuscleEnum.CHEST_LOWER, MuscleEnum.CHEST_UPPER)),
            ExerciseEntity(0, "Inclined Bench Press (Barbell)", "Bla bla bla",
                arrayListOf(MuscleEnum.CHEST, MuscleEnum.CHEST_LOWER, MuscleEnum.CHEST_UPPER)))

        return Single.just(LoadedExercisesResult(exercises, ArrayList()))
    }
}