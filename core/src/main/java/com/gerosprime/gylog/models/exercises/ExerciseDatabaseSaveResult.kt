package com.gerosprime.gylog.models.exercises

class ExerciseDatabaseSaveResult(val exerciseEntity: ExerciseEntity,
                                 val flag : Int, val itemIndex : Int) {
    object Flag {
        const val NEW = 0
        const val UPDATED = 1
    }
}