package com.gerosprime.gylog.models.exercises

import io.reactivex.Single

interface ExerciseDatabaseSaver {
    fun save(exercise : ExerciseEntity) : Single<ExerciseDatabaseSaveResult>
}