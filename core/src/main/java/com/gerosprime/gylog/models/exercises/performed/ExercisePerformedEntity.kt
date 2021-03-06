package com.gerosprime.gylog.models.exercises.performed

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.gerosprime.gylog.models.exercises.performedsets.PerformedSetEntity
import java.util.*

@Entity
data class ExercisePerformedEntity(@PrimaryKey var recordId : Long? = null,
                                   @ColumnInfo(index = true) val exerciseId : Long,
                                   val previousExercisePerformedId : Long,
                                   val name : String,
                                   var workoutSessionId : Long? = null,
                                   var workoutId : Long? = null,
                                   var performedDate : Date? = null) {
    @Ignore var performedSets : ArrayList<PerformedSetEntity> = arrayListOf()

}