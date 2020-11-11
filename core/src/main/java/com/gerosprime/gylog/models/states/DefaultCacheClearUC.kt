package com.gerosprime.gylog.models.states

import com.gerosprime.gylog.models.Result
import io.reactivex.Single

class DefaultCacheClearUC(private val editCacheEntityCache: EditProgramEntityCache)
    : EditCacheClearUC {


    override fun clear(): Single<ClearEditCacheResult>
            = Single.fromCallable {

        editCacheEntityCache.editProgram = null
        editCacheEntityCache.editWorkouts = arrayListOf()
        editCacheEntityCache.editExercisesTemplateMap = mutableMapOf()
        editCacheEntityCache.editExercisesTemplate = arrayListOf()
        editCacheEntityCache.editTemplateSets = arrayListOf()

        ClearEditCacheResult(Result.ResultConstant.RESULT_SUCCESS)
    }
}