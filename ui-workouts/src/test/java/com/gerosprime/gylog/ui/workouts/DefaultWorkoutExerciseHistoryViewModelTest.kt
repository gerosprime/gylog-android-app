package com.gerosprime.gylog.ui.workouts

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import com.gerosprime.gylog.base.FetchState
import com.gerosprime.gylog.models.workouts.history.WorkoutExerciseHistoryLoader
import com.gerosprime.gylog.models.workouts.history.WorkoutExerciseHistoryResult
import com.gerosprime.gylog.ui.workouts.history.DefaultWorkoutExerciseHistoryViewModel
import com.gerosprime.gylog.ui.workouts.history.WorkoutExerciseHistoryViewModel
import io.reactivex.Single
import org.hamcrest.CoreMatchers
import org.hamcrest.CoreMatchers.notNullValue
import org.junit.Assert
import org.junit.Assert.assertThat
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import org.junit.runners.JUnit4


@RunWith(JUnit4::class)
class DefaultWorkoutExerciseHistoryViewModelTest {

    @get:Rule
    val rule: TestRule = InstantTaskExecutorRule()

    lateinit var workoutExerciseHistoryLoader: WorkoutExerciseHistoryLoader

    lateinit var fetchStateMutableLiveData: MutableLiveData<FetchState>
    lateinit var  workoutExerciseHistoryMLD: MutableLiveData<WorkoutExerciseHistoryResult>

    lateinit var subject : WorkoutExerciseHistoryViewModel

    @Before
    fun setUp() {

        workoutExerciseHistoryLoader = FakeWorkoutExerciseHistoryLoader()

        fetchStateMutableLiveData = MutableLiveData()
        workoutExerciseHistoryMLD = MutableLiveData()

        subject = DefaultWorkoutExerciseHistoryViewModel(
            fetchStateMutableLiveData, workoutExerciseHistoryMLD, workoutExerciseHistoryLoader, null, null)
    }

    @Test
    fun loadHistory_success_workoutHistoryProvided() {
        subject.loadExerciseHistory(0L, 0L)

        val result = workoutExerciseHistoryMLD.value
        assertThat(result, notNullValue())
    }

    private class FakeWorkoutExerciseHistoryLoader : WorkoutExerciseHistoryLoader {
        override fun load(workoutId: Long, exerciseId: Long): Single<WorkoutExerciseHistoryResult> {
            return Single.just(WorkoutExerciseHistoryResult(workoutId, exerciseId, arrayListOf()))
        }

    }
}