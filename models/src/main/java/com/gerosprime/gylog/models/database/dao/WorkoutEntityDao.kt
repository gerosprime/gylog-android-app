package com.gerosprime.gylog.models.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.gerosprime.gylog.models.workouts.WorkoutEntity
import io.reactivex.Maybe

@Dao
interface WorkoutEntityDao {
    @Query("select * from WorkoutEntity")
    fun loadWorkouts() : List<WorkoutEntity>
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveWorkouts(workouts : List<WorkoutEntity>) : Maybe<List<Long>>
}