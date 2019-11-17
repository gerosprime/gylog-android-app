package com.gerosprime.gylog.models.programs

import com.gerosprime.gylog.models.database.GylogEntityDatabase
import io.reactivex.Single
import javax.inject.Inject

class DefaultProgramsDatabaseLoader
    @Inject constructor(private val database: GylogEntityDatabase)
    : ProgramsDatabaseLoader {
    override fun load(): Single<LoadedProgramDatabaseResult> {

        return Single.fromCallable {

            val programs = database.programEntityDao().getPrograms()
            LoadedProgramDatabaseResult(programs)

        }

    }

}