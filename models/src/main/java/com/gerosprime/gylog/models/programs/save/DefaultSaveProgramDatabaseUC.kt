package com.gerosprime.gylog.models.programs.save

import com.gerosprime.gylog.models.database.GylogEntityDatabase
import com.gerosprime.gylog.models.states.ModelCacheBuilder
import com.gerosprime.gylog.models.programs.ProgramEntity
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


            modelsCache.programsMap[program.recordId as Long] = program
            modelsCache.programs.add(program)

            for (workout in program.workouts) {
                modelsCache.workoutsMap[workout.recordId as Long] = workout
                modelsCache.workouts.add(workout)
            }

            SaveProgramDatabaseResult(program, 0)

        })

    }

}