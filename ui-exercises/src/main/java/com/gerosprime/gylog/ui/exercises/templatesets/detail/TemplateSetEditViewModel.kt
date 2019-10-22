package com.gerosprime.gylog.ui.exercises.templatesets.detail

import androidx.lifecycle.MutableLiveData
import com.gerosprime.gylog.base.FetchState
import com.gerosprime.gylog.models.exercises.templatesets.single.TemplateSetEditLoadResult
import com.gerosprime.gylog.models.exercises.templatesets.single.commit.TemplateSetCommitResult

interface TemplateSetEditViewModel {
    val fetchStateMLD : MutableLiveData<FetchState>
    val loadTemplateSetMLD : MutableLiveData<TemplateSetEditLoadResult>
    val commitMLD : MutableLiveData<TemplateSetCommitResult>

    fun loadTemplate(workoutIndex : Int, exerciseIndex : Int,
                     templateIndex : Int)

    fun commit(workoutIndex : Int, exerciseIndex : Int,
               templateIndex : Int,
               minReps: Int,
               reps: Int,
               weight: Float,
               counterWeight: Float,
               durationSeconds: Int,
               restTimeSeconds: Int)
}