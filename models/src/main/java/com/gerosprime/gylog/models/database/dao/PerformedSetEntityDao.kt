package com.gerosprime.gylog.models.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.gerosprime.gylog.models.exercises.performedsets.PerformedSetEntity

@Dao
interface PerformedSetEntityDao {
    @Query("select * from PerformedSetEntity")
    fun loadPerformedSets() : List<PerformedSetEntity>
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveSets(exercise : List<PerformedSetEntity>) : List<Long>
}