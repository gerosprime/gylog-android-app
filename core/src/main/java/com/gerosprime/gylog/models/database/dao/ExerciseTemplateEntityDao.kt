package com.gerosprime.gylog.models.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.gerosprime.gylog.models.exercises.templates.ExerciseTemplateEntity

@Dao
interface ExerciseTemplateEntityDao {
    @Query("select * from ExerciseTemplateEntity")
    fun loadExercises() : List<ExerciseTemplateEntity>
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveExercises(exercise : List<ExerciseTemplateEntity>) : List<Long>
}