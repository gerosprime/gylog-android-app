package com.gerosprime.gylog.ui.workouts.session

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import com.gerosprime.gylog.base.FetchState
import com.gerosprime.gylog.models.workouts.runningsession.create.WorkoutSessionCreationResult
import com.gerosprime.gylog.models.workouts.runningsession.create.WorkoutSessionCreator
import com.gerosprime.gylog.models.workouts.runningsession.load.WorkoutSessionCacheLoadResult
import com.gerosprime.gylog.models.workouts.runningsession.performedset.add.AddPerformedSetResult
import com.gerosprime.gylog.models.workouts.runningsession.performedset.edit.EditPerformedSetResult
import com.gerosprime.gylog.models.workouts.runningsession.performedset.remove.RemoveWorkoutSessionSetResult
import com.gerosprime.gylog.models.workouts.runningsession.performedset.remove.UnflagRemovePerformedSetResult
import io.reactivex.Single
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class DefaultWorkoutSessionViewModelTest {

    @get:Rule
    val rule: TestRule = InstantTaskExecutorRule()

    lateinit var fetchStateMLD: MutableLiveData<FetchState>
    lateinit var workoutSessionLoadMLD: MutableLiveData<WorkoutSessionCacheLoadResult>
    lateinit var workoutSessionCreateMLD: MutableLiveData<WorkoutSessionCreationResult>
    lateinit var addSetMutableLiveData: MutableLiveData<AddPerformedSetResult>
    lateinit var removeSetMutableLiveData: MutableLiveData<RemoveWorkoutSessionSetResult>
    lateinit var unFlagRemoveSetMutableLiveData: MutableLiveData<UnflagRemovePerformedSetResult>
    lateinit var completeSetMutableLiveData: MutableLiveData<EditPerformedSetResult>
    lateinit var sessionTimerMLD: MutableLiveData<String>
    lateinit var restTimerMLD: MutableLiveData<String>

    private lateinit var viewModel : DefaultWorkoutSessionViewModel

    object Constants {
        const val MOCK_WORKOUT_RECORD_ID : Long = 1
    }

    @Before
    fun init() {

        fetchStateMLD = MutableLiveData()
        workoutSessionLoadMLD = MutableLiveData()
        workoutSessionCreateMLD = MutableLiveData()
        addSetMutableLiveData = MutableLiveData()
        removeSetMutableLiveData = MutableLiveData()
        unFlagRemoveSetMutableLiveData = MutableLiveData()
        completeSetMutableLiveData = MutableLiveData()
        sessionTimerMLD = MutableLiveData()
        restTimerMLD = MutableLiveData()

        viewModel = DefaultWorkoutSessionViewModel(fetchStateMLD,
            workoutSessionLoadMLD, workoutSessionCreateMLD,
            addSetMutableLiveData, removeSetMutableLiveData,
            unFlagRemoveSetMutableLiveData, completeSetMutableLiveData,
            sessionTimerMLD, restTimerMLD)
    }

    @Test
    fun createWorkoutSession_success_resultReturnedWithNewWorkoutSession() {

    }

    class FakeWorkoutSessionCreator : WorkoutSessionCreator {
        override fun create(workoutEntityId: Long): Single<WorkoutSessionCreationResult> {
            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }
    }

}