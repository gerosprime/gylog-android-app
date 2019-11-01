package com.gerosprime.gylog.models.programs.save

import com.gerosprime.gylog.models.programs.ProgramEntity
import com.gerosprime.gylog.models.states.ModelsCache
import io.reactivex.Single
import javax.inject.Inject


class DefaultSaveProgramDatabaseUC
    @Inject constructor(private val modelsCache: ModelsCache) : SaveProgramDatabaseUC {

    override fun save(program: ProgramEntity):
            Single<SaveProgramDatabaseResult> = Single.fromCallable {

        var recordId = program.recordId

        if (recordId == null) {
            recordId = (modelsCache.programsMap.size + 1).toLong()
            modelsCache.programs.add(0, program)
        } else {

            val oldProgram = modelsCache.programsMap[recordId]
            val currentIndex = modelsCache.programs.indexOf(oldProgram)
            modelsCache.programs[currentIndex] = oldProgram!!

        }
        modelsCache.programsMap[recordId] = program
        SaveProgramDatabaseResult(program, 0)

    }
}