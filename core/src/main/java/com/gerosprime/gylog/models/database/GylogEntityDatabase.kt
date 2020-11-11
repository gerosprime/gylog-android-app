package com.gerosprime.gylog.models.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.gerosprime.gylog.models.body.fat.BodyFatEntity
import com.gerosprime.gylog.models.body.weight.BodyWeightEntity
import com.gerosprime.gylog.models.database.dao.*
import com.gerosprime.gylog.models.exercises.ExerciseEntity
import com.gerosprime.gylog.models.exercises.performed.ExercisePerformedEntity
import com.gerosprime.gylog.models.exercises.performedsets.PerformedSetEntity
import com.gerosprime.gylog.models.exercises.templates.ExerciseTemplateEntity
import com.gerosprime.gylog.models.exercises.templatesets.TemplateSetEntity
import com.gerosprime.gylog.models.programs.ProgramEntity
import com.gerosprime.gylog.models.workouts.WorkoutEntity
import com.gerosprime.gylog.models.workouts.runningsession.WorkoutSessionEntity

@Database(entities = [ProgramEntity::class, WorkoutEntity::class,
    ExerciseEntity::class, ExercisePerformedEntity::class, ExerciseTemplateEntity::class,
    PerformedSetEntity::class, TemplateSetEntity::class,
    WorkoutSessionEntity::class, BodyWeightEntity::class, BodyFatEntity::class], version = 1)
@TypeConverters(value = [DateConverter::class, MuscleEnumConverter::class])
abstract class GylogEntityDatabase : RoomDatabase() {
    abstract fun exerciseEntityDao() : ExerciseEntityDao
    abstract fun exercisePerformedDao() : ExercisePerformedEntityDao
    abstract fun exerciseTemplateEntityDao() : ExerciseTemplateEntityDao
    abstract fun performedSetEntityDao() : PerformedSetEntityDao
    abstract fun programEntityDao() : ProgramEntityDao
    abstract fun templateSetEntityDao() : TemplateSetEntityDao
    abstract fun workoutEntityDao() : WorkoutEntityDao
    abstract fun workoutSessionEntityDao() : WorkoutSessionEntityDao
    abstract fun bodyWeightEntityDao() : BodyWeightEntityDao
    abstract fun bodyFatEntityDao() : BodyFatEntityDao
}