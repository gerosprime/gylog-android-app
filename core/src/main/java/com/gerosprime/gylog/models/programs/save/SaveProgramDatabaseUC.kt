package com.gerosprime.gylog.models.programs.save

import com.gerosprime.gylog.models.programs.ProgramEntity
import io.reactivex.Single


interface SaveProgramDatabaseUC {
    fun save(program : ProgramEntity) : Single<SaveProgramDatabaseResult>
}