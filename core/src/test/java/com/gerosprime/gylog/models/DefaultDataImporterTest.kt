package com.gerosprime.gylog.models


import com.gerosprime.gylog.models.data.DataImporter
import com.gerosprime.gylog.models.data.DefaultDataImporter
import com.gerosprime.gylog.models.database.dao.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mockito
import java.io.File
import kotlin.jvm.javaClass as javaClass1

@RunWith(JUnit4::class)
class DefaultDataImporterTest {

    lateinit var subject : DataImporter

    lateinit var exerciseEntityDao : ExerciseEntityDao
    lateinit var exercisePerformedDao : ExercisePerformedEntityDao
    lateinit var exerciseTemplateEntityDao : ExerciseTemplateEntityDao
    lateinit var performedSetEntityDao : PerformedSetEntityDao
    lateinit var programEntityDao : ProgramEntityDao
    lateinit var templateSetEntityDao : TemplateSetEntityDao
    lateinit var workoutEntityDao : WorkoutEntityDao
    lateinit var workoutSessionEntityDao : WorkoutSessionEntityDao
    lateinit var bodyWeightEntityDao : BodyWeightEntityDao
    lateinit var bodyFatEntityDao : BodyFatEntityDao

    lateinit var dataFile : File

    @Before
    fun setUp() {
        dataFile = File(javaClass1.getResource("./fake_data_file").file)
        exerciseEntityDao = Mockito.mock(ExerciseEntityDao::class.java)
        exercisePerformedDao = Mockito.mock(ExercisePerformedEntityDao::class.java)
        exerciseTemplateEntityDao = Mockito.mock(ExerciseTemplateEntityDao::class.java)
        performedSetEntityDao = Mockito.mock(PerformedSetEntityDao::class.java)
        programEntityDao = Mockito.mock(ProgramEntityDao::class.java)
        templateSetEntityDao = Mockito.mock(TemplateSetEntityDao::class.java)
        workoutEntityDao = Mockito.mock(WorkoutEntityDao::class.java)
        workoutSessionEntityDao = Mockito.mock(WorkoutSessionEntityDao::class.java)
        bodyWeightEntityDao = Mockito.mock(BodyWeightEntityDao::class.java)
        bodyFatEntityDao = Mockito.mock(BodyFatEntityDao::class.java)

        subject = DefaultDataImporter(exerciseEntityDao,
            exercisePerformedDao, exerciseTemplateEntityDao,
            performedSetEntityDao, programEntityDao,
            templateSetEntityDao, workoutEntityDao,
            workoutSessionEntityDao, bodyWeightEntityDao, bodyFatEntityDao)
    }

    @Test
    fun importFromFile_success_allEntitiesInsertedToDatabase() {
        subject.importFromFile(dataFile).blockingGet()
    }

}