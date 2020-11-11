package com.gerosprime.gylog.models.programs

import io.reactivex.Maybe
import io.reactivex.Single

interface ProgramsDatabaseLoader {
    fun load() : Single<LoadedProgramDatabaseResult>
}