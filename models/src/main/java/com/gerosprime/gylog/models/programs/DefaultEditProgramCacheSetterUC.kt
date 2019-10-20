package com.gerosprime.gylog.models.programs

import com.gerosprime.gylog.models.states.EditProgramEntityState
import io.reactivex.Single
import javax.inject.Inject


class DefaultEditProgramCacheSetterUC @Inject constructor(private val editProgramEntityState: EditProgramEntityState)
    : EditProgramCacheSetterUseCase {

    override fun editProgramSetToCache(entity: ProgramEntity) : Single<EditProgramSetToCacheResult> {
        return Single.fromCallable {

            editProgramEntityState.newProgram = entity
            editProgramEntityState.workouts = entity.workouts
            EditProgramSetToCacheResult(editProgramEntityState.newProgram!!,
                editProgramEntityState.workouts!!)
        }
    }
}