package com.gerosprime.gylog.ui.workouts.session

import androidx.lifecycle.MutableLiveData
import com.gerosprime.gylog.base.FetchState
import com.gerosprime.gylog.models.workouts.runningsession.create.WorkoutSessionCreationResult
import com.gerosprime.gylog.models.workouts.runningsession.discard.RunningWorkoutSessionDiscardResult
import com.gerosprime.gylog.models.workouts.runningsession.finalizer.FinalizedRunningSessionResult
import com.gerosprime.gylog.models.workouts.runningsession.load.WorkoutSessionCacheLoadResult
import com.gerosprime.gylog.models.workouts.runningsession.performedset.add.AddPerformedSetResult
import com.gerosprime.gylog.models.workouts.runningsession.performedset.edit.EditPerformedSetResult
import com.gerosprime.gylog.models.workouts.runningsession.performedset.remove.RemoveWorkoutSessionSetResult
import com.gerosprime.gylog.models.workouts.runningsession.performedset.remove.UnflagRemovePerformedSetResult
import com.gerosprime.gylog.models.workouts.runningsession.save.WorkoutSessionSaveResult

interface WorkoutSessionViewModel {

    val fetchStateMLD : MutableLiveData<FetchState>
    val workoutSessionLoadMLD : MutableLiveData<WorkoutSessionCacheLoadResult>
    val workoutSessionCreateMLD : MutableLiveData<WorkoutSessionCreationResult>

    val addSetMutableLiveData : MutableLiveData<AddPerformedSetResult>
    val removeSetMutableLiveData : MutableLiveData<RemoveWorkoutSessionSetResult>
    val unFlagRemoveSetMutableLiveData : MutableLiveData<UnflagRemovePerformedSetResult>
    val completeSetMutableLiveData : MutableLiveData<EditPerformedSetResult>

    val finalizedSessionMLD : MutableLiveData<FinalizedRunningSessionResult>
    val savedSessionMLD : MutableLiveData<WorkoutSessionSaveResult>
    val discardSessionMLD: MutableLiveData<RunningWorkoutSessionDiscardResult>

    val sessionTimerMLD : MutableLiveData<String>
    val restTimerMLD : MutableLiveData<String>

    fun createWorkoutSession(workoutRecordId : Long)
    fun addSet(exercisePerformedIndex : Int)
    fun removeSet(exercisePerformedIndex : Int, setIndex: Int)
    fun unRemoveTemplateSet(exercisePerformedIndex : Int, setIndex: Int)
    fun editSet(exercisePerformedIndex : Int,
                setIndex: Int, reps : Int?, weight: Float?)

    fun resumeWorkoutSession(workoutRecordId: Long)

    fun isResting() : Boolean

    fun restSet(duration : Int)

    fun cancelRest()

    fun finishWorkoutSession()

    fun discardWorkoutSession()

}