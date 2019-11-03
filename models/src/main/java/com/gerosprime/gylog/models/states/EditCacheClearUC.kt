package com.gerosprime.gylog.models.states

import io.reactivex.Single


interface EditCacheClearUC {
    fun clear() : Single<ClearEditCacheResult>
}