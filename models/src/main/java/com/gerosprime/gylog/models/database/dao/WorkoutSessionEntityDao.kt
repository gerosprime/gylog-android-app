package com.gerosprime.gylog.models.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import com.gerosprime.gylog.models.exercises.performed.ExercisePerformedEntity
import com.gerosprime.gylog.models.exercises.performedsets.PerformedSetEntity
import com.gerosprime.gylog.models.workouts.runningsession.WorkoutSessionEntity
import java.util.ArrayList

@Dao
abstract class WorkoutSessionEntityDao {
    @Query("select * from WorkoutSessionEntity")
    abstract fun loadSessions() : List<WorkoutSessionEntity>
    @Insert
    abstract fun insertSession(session : WorkoutSessionEntity) : Long

    @Insert
    abstract fun insertPerformedExercises(exercises : List<ExercisePerformedEntity>) : List<Long>

    @Transaction
    open fun saveWholeSession(session : WorkoutSessionEntity) {
        val sessionId = insertSession(session)

        for (exercisePerformedEntity in session.exercisesPerformed) {
            exercisePerformedEntity.workoutSessionId = sessionId
        }
        val performedExercisesId = insertPerformedExercises(session.exercisesPerformed)

        for (p in 0 until session.exercisesPerformed.size) {


            val performedExercise = session.exercisesPerformed[p]
            performedExercise.recordId = performedExercisesId[p]
            for (performedSet in performedExercise.performedSets) {
                performedSet.exercisePerformedId = performedExercise.recordId
                performedSet.exerciseId = performedExercise.exerciseId
            }
            val performedSetIds = insertPerformedSets(performedExercise.performedSets)
            for (i in 0 until performedExercise.performedSets.size) {
                performedExercise.performedSets[i].recordId = performedSetIds[i]
            }
        }

    }

    @Insert
    abstract fun insertPerformedSets(performedSets: ArrayList<PerformedSetEntity>) : List<Long>


}