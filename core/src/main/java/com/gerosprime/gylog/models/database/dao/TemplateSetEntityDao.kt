package com.gerosprime.gylog.models.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.gerosprime.gylog.models.exercises.templatesets.TemplateSetEntity

@Dao
interface TemplateSetEntityDao {
    @Query("select * from TemplateSetEntity")
    fun loadTemplateSets() : List<TemplateSetEntity>
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveTemplateSets(exercise : List<TemplateSetEntity>) : List<Long>
}