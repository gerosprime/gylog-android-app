package com.gerosprime.gylog.models.programs.save

import com.gerosprime.gylog.models.database.GylogEntityDatabase
import com.gerosprime.gylog.models.programs.ProgramEntity
import com.gerosprime.gylog.models.states.ModelCacheBuilder
import com.gerosprime.gylog.models.states.ModelsCache
import io.reactivex.Single
import javax.inject.Inject


class DefaultSaveProgramDatabaseUC
    @Inject constructor(private val modelsCache: ModelsCache,
                        private val cacheBuilder: ModelCacheBuilder,
                        private val gylogEntityDatabase: GylogEntityDatabase) : SaveProgramDatabaseUC {

    override fun save(program: ProgramEntity):


            Single<SaveProgramDatabaseResult> {

        val programDao = gylogEntityDatabase.programEntityDao()

        return cacheBuilder.build().andThen(Single.fromCallable {

            programDao.saveWholeProgram(program)


            val recordId = program.recordId
            var index: Int
            if (!modelsCache.programsMap.containsKey(recordId)) {
                modelsCache.programs.add(program)
                index = modelsCache.programs.size - 1
            } else {
                index = -1
                for (i in 0..(modelsCache.programs.size)) {
                    if (modelsCache.programs[i].recordId == program.recordId) {
                        modelsCache.programs[i] = program
                        index = i
                        break
                    }
                }
            }
            // Insert or update existing
            modelsCache.programsMap[program.recordId as Long] = program

            for (workout in program.workouts) {
                modelsCache.workoutsMap[workout.recordId as Long] = workout
                modelsCache.workouts.add(workout)
            }

            SaveProgramDatabaseResult(program, index)

        })

    }

}