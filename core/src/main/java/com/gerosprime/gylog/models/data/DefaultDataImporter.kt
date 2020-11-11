package com.gerosprime.gylog.models.data

import android.content.Context
import com.gerosprime.gylog.models.database.dao.*
import com.google.gson.Gson
import io.reactivex.Single
import java.io.*

class DefaultDataImporter(private val exerciseEntityDao : ExerciseEntityDao,
                          private val exercisePerformedDao : ExercisePerformedEntityDao,
                          private val exerciseTemplateEntityDao : ExerciseTemplateEntityDao,
                          private val performedSetEntityDao : PerformedSetEntityDao,
                          private val programEntityDao : ProgramEntityDao,
                          private val templateSetEntityDao : TemplateSetEntityDao,
                          private val workoutEntityDao : WorkoutEntityDao,
                          private val workoutSessionEntityDao : WorkoutSessionEntityDao,
                          private val bodyWeightEntityDao : BodyWeightEntityDao,
                          private val bodyFatEntityDao : BodyFatEntityDao) : DataImporter {

    private val gson = Gson()

    override fun importFromAsset(fileName : String, context: Context): Single<DataImportResult> {

        return Single.fromCallable {
            BufferedReader(InputStreamReader(context.assets.open(fileName)))
        }.flatMap {
            importFromReader(it)
        }
    }

    override fun importFromFile(file: File): Single<DataImportResult> {
        return Single.fromCallable {
            BufferedReader(FileReader(file))
        }.flatMap {
            importFromReader(it)
        }
    }

    private fun importFromReader(reader : Reader): Single<DataImportResult> {

        return Single.fromCallable {

            val importedData : ImportedJSONData
                    = gson.fromJson(reader, ImportedJSONData::class.java)

            exerciseEntityDao.saveExercises(importedData.exercises)
            exercisePerformedDao.saveExercises(importedData.performedExercises)
            exerciseTemplateEntityDao.saveExercises(importedData.templateExercises)
            performedSetEntityDao.saveSets(importedData.performedSets)
            programEntityDao.savePrograms(importedData.programs)
            templateSetEntityDao.saveTemplateSets(importedData.templateSets)
            workoutEntityDao.saveWorkouts(importedData.workouts)
            workoutSessionEntityDao.insertSessions(importedData.workoutsSessions)
            bodyWeightEntityDao.saveBodyWeights(importedData.workoutBodyWeightEntities)
            bodyFatEntityDao.saveMultiple(importedData.bodyFatEntities)

            DataImportResult()
        }
    }

}
