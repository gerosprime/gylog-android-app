package com.gerosprime.gylog.models.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.gerosprime.gylog.models.body.weight.BodyWeightEntity

@Dao
interface BodyWeightEntityDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun save(entity : BodyWeightEntity) : Long

    @Query("select * from BodyWeightEntity")
    fun loadBodyWeights(): List<BodyWeightEntity>
}