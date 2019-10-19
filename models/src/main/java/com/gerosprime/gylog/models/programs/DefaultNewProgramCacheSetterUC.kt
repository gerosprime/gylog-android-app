package com.gerosprime.gylog.models.programs

import com.gerosprime.gylog.models.states.AddProgramEntityState
import com.gerosprime.gylog.models.workouts.WorkoutEntity
import io.reactivex.Single
import javax.inject.Inject


class DefaultNewProgramCacheSetterUC @Inject constructor(private val addProgramEntityState: AddProgramEntityState)
    : NewProgramCacheSetterUseCase {

    override fun newProgramSetToCache(entity: ProgramEntity) : Single<NewProgramSetToCacheResult> {
        return Single.fromCallable {

            addProgramEntityState.newProgram = entity
            addProgramEntityState.workouts = entity.workouts as ArrayList<WorkoutEntity>?
            NewProgramSetToCacheResult(addProgramEntityState.newProgram!!,
                addProgramEntityState.workouts!!)
        }
    }
}