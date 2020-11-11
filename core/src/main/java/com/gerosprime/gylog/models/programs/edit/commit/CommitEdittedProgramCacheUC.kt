package com.gerosprime.gylog.models.programs.edit.commit

import io.reactivex.Single


interface CommitEdittedProgramCacheUC {
    fun commit(name : String, description : String,
               imageUri : String) : Single<CommitEdittedProgramCacheResult>
}