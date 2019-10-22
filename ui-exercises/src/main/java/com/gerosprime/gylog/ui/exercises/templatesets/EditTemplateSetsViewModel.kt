package com.gerosprime.gylog.ui.exercises.templatesets

import androidx.lifecycle.MutableLiveData
import com.gerosprime.gylog.base.FetchState
import com.gerosprime.gylog.models.exercises.templates.ExerciseTemplateEntity
import com.gerosprime.gylog.models.exercises.templatesets.LoadTemplateSetsToCacheResult
import com.gerosprime.gylog.models.exercises.templatesets.TemplateSetEntity
import com.gerosprime.gylog.models.exercises.templatesets.add.CreateTemplateSetToCacheResult
import com.gerosprime.gylog.models.exercises.templatesets.commit.CommitTemplateSetsToWorkoutResult
import com.gerosprime.gylog.models.exercises.templatesets.remove.RemoveTemplateFromCacheResult


interface EditTemplateSetsViewModel {

    val loadTemplateSetsMutableLiveData : MutableLiveData<LoadTemplateSetsToCacheResult>
    val fetchStateMutableLiveData : MutableLiveData<FetchState>

    val removeTemplateResultMutableLiveData : MutableLiveData<RemoveTemplateFromCacheResult>
    val addTemplateResultMutableLiveData : MutableLiveData<CreateTemplateSetToCacheResult>
    val commitTemplateResultMutableLiveData : MutableLiveData<CommitTemplateSetsToWorkoutResult>

    fun loadTemplateSets(workoutIndex : Int, workoutExercisesIndex : Int)
    fun addTemplate(workoutIndex : Int, workoutExercisesIndex : Int)
    fun commitTemplates(workoutIndex : Int, workoutExercisesIndex : Int)
    fun removeTemplate(templatesetIndex: Int,
                       workoutIndex : Int, workoutExercisesIndex : Int)

}