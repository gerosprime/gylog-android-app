package com.gerosprime.gylog.models.programs.detail

import com.gerosprime.gylog.models.states.ModelCacheBuilder
import com.gerosprime.gylog.models.programs.ProgramEntity
import com.gerosprime.gylog.models.states.ModelsCache
import com.gerosprime.gylog.models.workouts.WorkoutEntity
import io.reactivex.Single
import javax.inject.Inject

class DefaultProgramModelCacheLoader
    @Inject constructor(private val modelsCache: ModelsCache,
                        private val cacheBuilder: ModelCacheBuilder
    )
    : ProgramModelCacheLoader {

    override fun loadFromCache(programRecordId: Long): Single<LoadProgramFromCacheResult>
            = cacheBuilder.build().andThen(Single.fromCallable {

        val program = modelsCache.programsMap[programRecordId] as ProgramEntity
        LoadProgramFromCacheResult(program, programRecordId)
    })

}