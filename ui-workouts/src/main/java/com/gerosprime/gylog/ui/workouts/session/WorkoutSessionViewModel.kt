package com.gerosprime.gylog.ui.workouts.session

import androidx.lifecycle.LiveData
import com.gerosprime.gylog.base.FetchState
import com.gerosprime.gylog.models.workouts.runningsession.create.WorkoutSessionCreationResult
import com.gerosprime.gylog.models.workouts.runningsession.discard.RunningWorkoutSessionDiscardResult
import com.gerosprime.gylog.models.workouts.runningsession.finalizer.FinalizedRunningSessionResult
import com.gerosprime.gylog.models.workouts.runningsession.load.WorkoutSessionCacheLoadResult
import com.gerosprime.gylog.models.workouts.runningsession.performedset.add.AddPerformedSetResult
import com.gerosprime.gylog.models.workouts.runningsession.performedset.edit.ClearPerformedSetResult
import com.gerosprime.gylog.models.workouts.runningsession.performedset.edit.EditPerformedSetResult
import com.gerosprime.gylog.models.workouts.runningsession.performedset.remove.RemoveWorkoutSessionSetResult
import com.gerosprime.gylog.models.workouts.runningsession.performedset.remove.UnflagRemovePerformedSetResult
import com.gerosprime.gylog.models.workouts.runningsession.save.WorkoutSessionSaveResult
import java.util.*

interface WorkoutSessionViewModel {

    val fetchStateLiveData : LiveData<FetchState>
    val workoutSessionLoadLiveData : LiveData<WorkoutSessionCacheLoadResult>
    val workoutSessionCreateLiveData : LiveData<WorkoutSessionCreationResult>

    val addSetLiveData : LiveData<AddPerformedSetResult>
    val removeSetLiveData : LiveData<RemoveWorkoutSessionSetResult>
    val unFlagRemoveSetLiveData : LiveData<UnflagRemovePerformedSetResult>
    val completeSetLiveData : LiveData<EditPerformedSetResult>
    val clearSetLiveData : LiveData<ClearPerformedSetResult>

    val finalizedSessionLiveData : LiveData<FinalizedRunningSessionResult>
    val savedSessionLiveData : LiveData<WorkoutSessionSaveResult>
    val discardSessionLiveData: LiveData<RunningWorkoutSessionDiscardResult>

    val sessionTimerLiveData : LiveData<String>
    val restTimerLiveData : LiveData<String>

    fun createWorkoutSession(workoutRecordId : Long)
    fun addSet(exercisePerformedIndex : Int)
    fun removeSet(exercisePerformedIndex : Int, setIndex: Int)
    fun unRemoveTemplateSet(exercisePerformedIndex : Int, setIndex: Int)
    fun editSet(exercisePerformedIndex : Int,
                setIndex: Int, reps : Int?, weight: Float?,
                datePerformed : Date?)

    fun clearSet(exercisePerformedIndex : Int, setIndex: Int)

    fun resumeWorkoutSession(workoutRecordId: Long)

    fun isResting() : Boolean

    fun restSet(duration : Int)

    fun cancelRest()

    fun finishWorkoutSession()

    fun discardWorkoutSession()

}