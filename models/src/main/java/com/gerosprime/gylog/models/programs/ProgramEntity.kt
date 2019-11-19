package com.gerosprime.gylog.models.programs

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.gerosprime.gylog.models.workouts.WorkoutEntity

@Entity
data class ProgramEntity (@PrimaryKey var recordId: Long? = null,
               var name : String? = null, var description : String? = null) {

     @Ignore
     var workouts : ArrayList<WorkoutEntity> = arrayListOf()

    fun deepCopy() : ProgramEntity {
        val copyProgram = ProgramEntity(recordId, name, description)
        val copyWorkouts : ArrayList<WorkoutEntity> = arrayListOf()
        copyProgram.workouts = copyWorkouts

        for (workout in workouts) {
            copyWorkouts.add(workout.deepCopy())
        }

        return copyProgram
    }

}