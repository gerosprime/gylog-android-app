package com.gerosprime.gylog.models.database.dao

import androidx.room.*
import com.gerosprime.gylog.models.exercises.templates.ExerciseTemplateEntity
import com.gerosprime.gylog.models.exercises.templatesets.TemplateSetEntity
import com.gerosprime.gylog.models.programs.ProgramEntity
import com.gerosprime.gylog.models.workouts.WorkoutEntity

@Dao
abstract class ProgramEntityDao {
    @Query("select * from ProgramEntity")
    abstract fun getPrograms() : List<ProgramEntity>
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun saveProgram(program : ProgramEntity) : Long

    @Insert
    abstract fun saveWorkouts(workouts : List<WorkoutEntity>) : List<Long>

    @Insert
    abstract fun saveExerciseTemplates(templates : List<ExerciseTemplateEntity>) : List<Long>

    @Insert
    abstract fun saveTemplatesSet(templateSets : List<TemplateSetEntity>) : List<Long>

    @Transaction
    open fun saveWholeProgram(program: ProgramEntity) {
        val programId = saveProgram(program)
        program.recordId = programId

        for (workout in program.workouts) {
            workout.programId = programId
        }

        val workoutIds = saveWorkouts(program.workouts)
        for (i in 0 until program.workouts.size) {
            val workout = program.workouts[i]
            workout.recordId = workoutIds[i]

            // Save workout exercises
            for (exercise in workout.exercises) {
                exercise.workoutId = workout.recordId
            }
            val exerciseIds = saveExerciseTemplates(workout.exercises)
            for (e in 0 until workout.exercises.size) {

                val exercise = workout.exercises[e]
                exercise.recordId = exerciseIds[e]
                for (templateSet in exercise.setTemplates) {
                    templateSet.exerciseTemplateId = exercise.recordId
                }
                val templateSetIds = saveTemplatesSet(exercise.setTemplates)
                for (ts in 0..exercise.setTemplates.size) {
                    exercise.setTemplates[ts].recordId = templateSetIds[ts]
                }
            }

        }

    }

}