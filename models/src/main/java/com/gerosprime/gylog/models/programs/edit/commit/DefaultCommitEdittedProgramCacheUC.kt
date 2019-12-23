package com.gerosprime.gylog.models.programs.edit.commit

import com.gerosprime.gylog.models.programs.ProgramEntity
import com.gerosprime.gylog.models.states.EditProgramEntityCache
import io.reactivex.Single
import javax.inject.Inject

class DefaultCommitEdittedProgramCacheUC
    @Inject constructor(private val editProgramEntityCache: EditProgramEntityCache)
    : CommitEdittedProgramCacheUC {

    override fun commit(name : String, description : String, imageUri : String)
            : Single<CommitEdittedProgramCacheResult>
            = Single.fromCallable {

        val program = editProgramEntityCache.editProgram as ProgramEntity

        program.name = name
        program.description = description
        program.imageUri = imageUri

         program.workouts = editProgramEntityCache.editWorkouts

        editProgramEntityCache.editWorkouts = arrayListOf()

        editProgramEntityCache.editExercisesTemplate = arrayListOf()
        editProgramEntityCache.editTemplateSets = arrayListOf()
        editProgramEntityCache.editExercisesTemplateMap = mutableMapOf()

        CommitEdittedProgramCacheResult(program)
    }

}