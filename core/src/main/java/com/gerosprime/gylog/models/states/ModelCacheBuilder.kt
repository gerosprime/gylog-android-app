package com.gerosprime.gylog.models.states

import io.reactivex.Completable

interface ModelCacheBuilder {

    fun build() : Completable

}
