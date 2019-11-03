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
            program.recordId = recordId
            modelsCache.programs.add(0, program)
        } else {

            for (i in 0..modelsCache.programs.size) {
                if (modelsCache.programs[i].recordId == recordId) {
                    modelsCache.programs[i] = program
                    break
                }
            }

        }
        modelsCache.programsMap[recordId] = program
        SaveProgramDatabaseResult(program, 0)

    }
}