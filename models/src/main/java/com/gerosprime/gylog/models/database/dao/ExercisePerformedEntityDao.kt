package com.gerosprime.gylog.models.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.gerosprime.gylog.models.exercises.performed.ExercisePerformedEntity

@Dao
interface ExercisePerformedEntityDao {
    @Query("select * from ExercisePerformedEntity")
    fun loadExercises() : List<ExercisePerformedEntity>
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveExercises(exercise : List<ExercisePerformedEntity>) : List<Long>
}