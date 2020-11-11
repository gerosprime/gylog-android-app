package com.gerosprime.gylog.models.programs

import com.gerosprime.gylog.models.states.ModelCacheBuilder
import com.gerosprime.gylog.models.states.ModelsCache
import io.reactivex.Single
import javax.inject.Inject

class DefaultProgramsCacheCacheLoader
    @Inject constructor(private val modelsCache: ModelsCache,
                        private val cacheBuilder: ModelCacheBuilder
    ): ProgramsCacheLoader {

    override fun loadUserPrograms(): Single<LoadedProgramCacheResult> {

        return cacheBuilder.build().andThen(Single.fromCallable {

//            val programs : ArrayList<ProgramEntity> = arrayListOf()
//            for (mutableEntry in modelsCache.programsMap) {
//                programs.add(mutableEntry.value)
//            }

            LoadedProgramCacheResult(modelsCache.programs, ArrayList())
        })

    }
}