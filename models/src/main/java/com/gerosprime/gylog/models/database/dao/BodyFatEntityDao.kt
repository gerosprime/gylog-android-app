package com.gerosprime.gylog.models.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.gerosprime.gylog.models.body.fat.BodyFatEntity

@Dao
interface BodyFatEntityDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun save(entity : BodyFatEntity) : Long

    @Query("select * from BodyFatEntity")
    fun loadBodyFats(): List<BodyFatEntity>
}