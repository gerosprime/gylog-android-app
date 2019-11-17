package com.gerosprime.gylog.models.exercises.performed

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.gerosprime.gylog.models.exercises.performedsets.PerformedSetEntity

@Entity
data class ExercisePerformedEntity(@PrimaryKey val recordId : Long? = null,
                                   @ColumnInfo(index = true) val exerciseId : Long,
                                   val previousExercisePerformedId : Long,
                                   val name : String) {
    @Ignore var performedSets : ArrayList<PerformedSetEntity> = arrayListOf()
}