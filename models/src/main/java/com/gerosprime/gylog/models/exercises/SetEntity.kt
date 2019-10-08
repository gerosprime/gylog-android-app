package com.gerosprime.gylog.models.exercises

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class SetEntity(@PrimaryKey val recordId : Long, val reps : Int,
                     val weight:Float, val counterWeight:Float,
                     val durationSeconds:Int)