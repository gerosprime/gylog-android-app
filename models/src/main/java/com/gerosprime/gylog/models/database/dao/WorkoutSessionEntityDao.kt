package com.gerosprime.gylog.models.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.gerosprime.gylog.models.workouts.runningsession.WorkoutSessionEntity

@Dao
interface WorkoutSessionEntityDao {
    @Query("select * from WorkoutSessionEntity")
    fun loadSessions() : List<WorkoutSessionEntity>
    @Insert
    fun insertSession(session : WorkoutSessionEntity) : Long
}