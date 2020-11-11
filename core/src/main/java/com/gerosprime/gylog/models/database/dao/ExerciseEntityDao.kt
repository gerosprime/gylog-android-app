package com.gerosprime.gylog.models.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.gerosprime.gylog.models.exercises.ExerciseEntity

@Dao
interface ExerciseEntityDao {
    @Query("select * from ExerciseEntity")
    fun loadExercises() : List<ExerciseEntity>
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveExercise(exercise : ExerciseEntity) : Long
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveExercises(exercises : List<ExerciseEntity>) : List<Long>
}