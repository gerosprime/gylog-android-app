package com.gerosprime.gylog.ui.workouts.session

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import com.gerosprime.gylog.base.FetchState
import com.gerosprime.gylog.models.exercises.performedsets.PerformedSetEntity
import com.gerosprime.gylog.models.states.ModelCacheBuilder
import com.gerosprime.gylog.models.states.ModelsCache
import com.gerosprime.gylog.models.states.RunningWorkoutSessionCache
import com.gerosprime.gylog.models.workouts.WorkoutEntity
import com.gerosprime.gylog.models.workouts.runningsession.WorkoutSessionEntity
import com.gerosprime.gylog.models.workouts.runningsession.create.DefaultWorkoutSessionCreator
import com.gerosprime.gylog.models.workouts.runningsession.create.WorkoutSessionCreationResult
import com.gerosprime.gylog.models.workouts.runningsession.create.WorkoutSessionCreator
import com.gerosprime.gylog.models.workouts.runningsession.discard.RunningWorkoutSessionDiscardResult
import com.gerosprime.gylog.models.workouts.runningsession.discard.RunningWorkoutSessionDiscardUC
import com.gerosprime.gylog.models.workouts.runningsession.finalizer.FinalizedRunningSessionResult
import com.gerosprime.gylog.models.workouts.runningsession.finalizer.RunningWorkoutSessionFinalizer
import com.gerosprime.gylog.models.workouts.runningsession.load.RunningWorkoutSessionLoader
import com.gerosprime.gylog.models.workouts.runningsession.load.WorkoutSessionCacheLoadResult
import com.gerosprime.gylog.models.workouts.runningsession.load.WorkoutSessionInfoLoadResult
import com.gerosprime.gylog.models.workouts.runningsession.performedset.add.AddPerformedSetResult
import com.gerosprime.gylog.models.workouts.runningsession.performedset.add.AddPerformedSetUC
import com.gerosprime.gylog.models.workouts.runningsession.performedset.edit.ClearPerformedSetResult
import com.gerosprime.gylog.models.workouts.runningsession.performedset.edit.EditPerformedSetResult
import com.gerosprime.gylog.models.workouts.runningsession.performedset.edit.EditPerformedSetUC
import com.gerosprime.gylog.models.workouts.runningsession.performedset.remove.RemovePerformedSetUC
import com.gerosprime.gylog.models.workouts.runningsession.performedset.remove.RemoveWorkoutSessionSetResult
import com.gerosprime.gylog.models.workouts.runningsession.performedset.remove.UnRemovePerformedSetUC
import com.gerosprime.gylog.models.workouts.runningsession.performedset.remove.UnflagRemovePerformedSetResult
import com.gerosprime.gylog.models.workouts.runningsession.save.WorkoutSessionSaveResult
import com.gerosprime.gylog.models.workouts.runningsession.save.WorkoutSessionSaver
import io.reactivex.Completable
import io.reactivex.Single
import org.hamcrest.CoreMatchers
import org.hamcrest.CoreMatchers.*
import org.junit.Assert
import org.junit.Assert.assertThat
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import java.util.*

@RunWith(JUnit4::class)
class DefaultWorkoutSessionViewModelTest {

    @get:Rule
    val rule: TestRule = InstantTaskExecutorRule()

    lateinit var runningSession : RunningWorkoutSessionCache
    lateinit var modelCache : ModelsCache

    lateinit var workoutEntity: WorkoutEntity
    lateinit var workoutSessionEntity: WorkoutSessionEntity

    lateinit var fetchStateMLD: MutableLiveData<FetchState>
    lateinit var workoutSessionLoadMLD: MutableLiveData<WorkoutSessionCacheLoadResult>
    lateinit var workoutSessionCreateMLD: MutableLiveData<WorkoutSessionCreationResult>
    lateinit var addSetMutableLiveData: MutableLiveData<AddPerformedSetResult>
    lateinit var removeSetMutableLiveData: MutableLiveData<RemoveWorkoutSessionSetResult>
    lateinit var unFlagRemoveSetMutableLiveData: MutableLiveData<UnflagRemovePerformedSetResult>
    lateinit var completeSetMutableLiveData: MutableLiveData<EditPerformedSetResult>
    lateinit var clearSetMutableLiveData: MutableLiveData<ClearPerformedSetResult>

    lateinit var cacheBuilder: ModelCacheBuilder

    lateinit var sessionTimerMLD: MutableLiveData<String>
    lateinit var restTimerMLD: MutableLiveData<String>
    lateinit var finalizedSessionMLD: MutableLiveData<FinalizedRunningSessionResult>
    lateinit var savedSessionMLD: MutableLiveData<WorkoutSessionSaveResult>
    lateinit var discardSessionMLD: MutableLiveData<RunningWorkoutSessionDiscardResult>

    lateinit var createWorkoutSessionUC : WorkoutSessionCreator
    lateinit var runningWorkoutSessionLoader: RunningWorkoutSessionLoader
    lateinit var sessionFinalizer: RunningWorkoutSessionFinalizer
    lateinit var sessionSaver : WorkoutSessionSaver
    lateinit var sessionDiscardUC : RunningWorkoutSessionDiscardUC


    lateinit var addPerformedSetUC: AddPerformedSetUC
    lateinit var removePerformedSetUC: RemovePerformedSetUC
    lateinit var unRemovePerformedSetUC: UnRemovePerformedSetUC
    lateinit var editPerformedSetUC: EditPerformedSetUC

    private lateinit var viewModel : DefaultWorkoutSessionViewModel

    object Constants {
        const val MOCK_WORKOUT_RECORD_ID : Long = 1
    }

    @Before
    fun init() {

        workoutEntity = WorkoutEntity(recordId = 1L, name = "Test", description = "",
            programId = 1L, totalTimeSeconds = 3600, day = 1, lastWorkoutSessionId = null)
        workoutEntity.exercises = arrayListOf()

        workoutSessionEntity = WorkoutSessionEntity(1L, workoutEntity.recordId!!,
            10L, null, null)
        workoutSessionEntity.exercisesPerformed = arrayListOf()

        modelCache = ModelsCache(programs = arrayListOf(),
            programsMap = Collections.synchronizedMap(mutableMapOf()),
            workouts = arrayListOf(), workoutsMap = Collections.synchronizedMap(mutableMapOf()),
            exercisesMap = Collections.synchronizedMap(mutableMapOf()), exercisesList = arrayListOf(),
            templateExercisesMap = Collections.synchronizedMap(mutableMapOf()), templateExercises = arrayListOf(),
            templateSets = arrayListOf(), templateSetsMap = Collections.synchronizedMap(mutableMapOf()),
            performedExercisesMap = Collections.synchronizedMap(mutableMapOf()), performedExercises = arrayListOf(),
            performedSetsMap = Collections.synchronizedMap(mutableMapOf()), performedSets = arrayListOf(),
            bodyWeightMap = Collections.synchronizedMap(mutableMapOf()), bodyFatMap = Collections.synchronizedMap(mutableMapOf()))

        modelCache.workoutsMap[workoutEntity.recordId!!] = workoutEntity

        runningSession = RunningWorkoutSessionCache(null, arrayListOf())

        cacheBuilder = FakeCacheBuilder()

        clearSetMutableLiveData = MutableLiveData()
        fetchStateMLD = MutableLiveData()
        workoutSessionLoadMLD = MutableLiveData()
        workoutSessionCreateMLD = MutableLiveData()
        addSetMutableLiveData = MutableLiveData()
        removeSetMutableLiveData = MutableLiveData()
        unFlagRemoveSetMutableLiveData = MutableLiveData()
        completeSetMutableLiveData = MutableLiveData()
        sessionTimerMLD = MutableLiveData()
        restTimerMLD = MutableLiveData()

        finalizedSessionMLD = MutableLiveData()
        savedSessionMLD = MutableLiveData()
        discardSessionMLD = MutableLiveData()

        createWorkoutSessionUC  = DefaultWorkoutSessionCreator(modelCache, runningSession, cacheBuilder)
        runningWorkoutSessionLoader = FakeWorkoutSessionLoader()
        sessionFinalizer = FakeSessionFinalizer()
        sessionSaver  = FakeSessionSaver()
        sessionDiscardUC  = FakeSessionDiscard()

        addPerformedSetUC = FakeAddSetUC()
        removePerformedSetUC = FakeRemoveSetUC()
        unRemovePerformedSetUC = FakeUnRemoveSetUC()
        editPerformedSetUC = FakeEditPerformedSetUC()


        viewModel = DefaultWorkoutSessionViewModel(fetchStateMLD,
            workoutSessionLoadMLD, workoutSessionCreateMLD,
            addSetMutableLiveData, removeSetMutableLiveData,
            unFlagRemoveSetMutableLiveData, completeSetMutableLiveData,
            clearSetMutableLiveData, sessionTimerMLD, restTimerMLD, finalizedSessionMLD,
            savedSessionMLD, discardSessionMLD,
            createWorkoutSessionUC, runningWorkoutSessionLoader,
            sessionFinalizer, sessionSaver, sessionDiscardUC,
            addPerformedSetUC, removePerformedSetUC,
            unRemovePerformedSetUC, editPerformedSetUC, null, null)

    }

    @Test
    fun createWorkoutSession_success_resultReturnedWithNewWorkoutSession() {

        viewModel.createWorkoutSession(1L)

        val result = workoutSessionCreateMLD.value
        val workoutSession : WorkoutSessionEntity? = result?.workoutSessionEntity


        assertThat(result, notNullValue())
        assertThat(workoutSession?.workoutId, `is`(workoutEntity.recordId))
        assertThat(workoutSession?.startDate, notNullValue())

    }

    @Test
    fun discardWorkoutSession_success_mutableLiveDataNotified() {
        viewModel.discardWorkoutSession()

        val result = discardSessionMLD.value
        assertThat(result, notNullValue())

        // assertThat(runningSession.prePerformedExercises, nullValue())
        // assertThat(runningSession.workoutSessionEntity, nullValue())
    }

    private class FakeCacheBuilder : ModelCacheBuilder {
        override fun build(): Completable {
            return Completable.complete()
        }

    }

    private class FakeEditPerformedSetUC : EditPerformedSetUC {

        override fun clear(
            exercisePerformedIndex: Int,
            setIndex: Int
        ): Single<ClearPerformedSetResult> {
            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }

        override fun edit(
            exercisePerformedIndex: Int,
            setIndex: Int,
            reps: Int?,
            weight: Float?,
            performedDate: Date?
        ): Single<EditPerformedSetResult> {
            TODO()
        }
    }

    private class FakeUnRemoveSetUC : UnRemovePerformedSetUC {
        override fun unflag(
            exercisePerformedIndex: Int,
            setIndex: Int
        ): Single<UnflagRemovePerformedSetResult> {
            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }
    }

    private class FakeRemoveSetUC : RemovePerformedSetUC {
        override fun remove(
            exercisePerformedIndex: Int,
            setIndex: Int
        ): Single<RemoveWorkoutSessionSetResult> {
            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }
    }

    private class FakeAddSetUC : AddPerformedSetUC {
        override fun add(exercisePerformedIndex: Int): Single<AddPerformedSetResult> {
            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }
    }

    private class FakeSessionSaver : WorkoutSessionSaver {
        override fun save(session: WorkoutSessionEntity): Single<WorkoutSessionSaveResult> {
            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }
    }

    private class FakeSessionDiscard : RunningWorkoutSessionDiscardUC {
        override fun discard(): Single<RunningWorkoutSessionDiscardResult> {
            return Single.just(RunningWorkoutSessionDiscardResult())
        }
    }

    private class FakeSessionFinalizer : RunningWorkoutSessionFinalizer {
        override fun finalizeSession(): Single<FinalizedRunningSessionResult> {
            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }
    }

    private class FakeWorkoutSessionCreator : WorkoutSessionCreator {
        override fun create(workoutEntityId: Long): Single<WorkoutSessionCreationResult> {
            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }
    }

    private class FakeWorkoutSessionLoader : RunningWorkoutSessionLoader {
        override fun load(): Single<WorkoutSessionCacheLoadResult> {
            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }

        override fun loadInfo(): Single<WorkoutSessionInfoLoadResult> {
            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }
    }



}