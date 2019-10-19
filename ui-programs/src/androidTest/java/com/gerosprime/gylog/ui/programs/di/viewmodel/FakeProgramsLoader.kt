package com.gerosprime.gylog.ui.programs.di.viewmodel

import com.gerosprime.gylog.models.programs.LoadedProgramResult
import com.gerosprime.gylog.models.programs.ProgramEntity
import com.gerosprime.gylog.models.programs.ProgramsLoader
import io.reactivex.Single

class FakeProgramsLoader : ProgramsLoader {
    override fun loadUserPrograms(): Single<LoadedProgramResult> {

        var userList = listOf(ProgramEntity(0,
            "BLS Phase 1", "", arrayListOf()),
            ProgramEntity(0,
                "BLS Phase 2", "", arrayListOf()),
            ProgramEntity(0,
                "BLS Phase 3", "", arrayListOf()),
            ProgramEntity(0,
                "BLS Phase 3", "", arrayListOf()),
            ProgramEntity(0,
                "BLS Phase 3", "", arrayListOf()),
            ProgramEntity(0,
                "BLS Phase 3", "", arrayListOf()),
            ProgramEntity(0,
                "BLS Phase 3", "", arrayListOf()),
            ProgramEntity(0,
                "BLS Phase 3", "", arrayListOf()))

        return Single.just(LoadedProgramResult(userList, userList))
    }
}