package com.gerosprime.gylog.models.programs.edit.load

import com.gerosprime.gylog.models.programs.ProgramEntity
import com.gerosprime.gylog.models.states.EditProgramEntityCache
import com.gerosprime.gylog.models.states.ModelsCache
import com.gerosprime.gylog.models.workouts.WorkoutEntity
import io.reactivex.Single
import javax.inject.Inject


class DefaultEditProgramCacheSetterUC @Inject constructor(
    private val editProgramEntityCache: EditProgramEntityCache,
    private val modelsCache: ModelsCache)
    : EditProgramCacheSetterUseCase {

    override fun editProgramSetToCache(programId : Long?) : Single<EditProgramSetToCacheResult> {
        return Single.fromCallable {

            if (programId == null) {
                editProgramEntityCache.editProgram =
                    ProgramEntity(workouts = arrayListOf())
            } else {

                val programCopy = modelsCache.programsMap[programId]!!.deepCopy()
                editProgramEntityCache.editProgram = programCopy

            }

            val workouts = editProgramEntityCache.editProgram!!.workouts!!
            val copyWorkouts : ArrayList<WorkoutEntity> = arrayListOf()
            for (workout in workouts) {
                copyWorkouts.add(workout.deepCopy())
            }
            editProgramEntityCache.editWorkouts = copyWorkouts

            EditProgramSetToCacheResult(
                editProgramEntityCache.editProgram!!,
                copyWorkouts
            )
        }
    }
}