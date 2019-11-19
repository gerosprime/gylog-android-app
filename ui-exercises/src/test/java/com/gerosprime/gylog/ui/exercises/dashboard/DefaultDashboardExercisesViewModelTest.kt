package com.gerosprime.gylog.ui.exercises.dashboard

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import com.gerosprime.gylog.base.FetchState
import com.gerosprime.gylog.models.exercises.ExerciseEntity
import com.gerosprime.gylog.models.exercises.ExercisesCacheLoader
import com.gerosprime.gylog.models.exercises.LoadedExercisesResult
import io.reactivex.Single
import org.hamcrest.CoreMatchers
import org.junit.Before

import org.junit.Assert.*
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import java.lang.RuntimeException

class DefaultDashboardExercisesViewModelTest {

    @get:Rule
    val rule: TestRule = InstantTaskExecutorRule()

    lateinit var programLoader : FakeExerciseCacheLoader

    private lateinit var viewModel: DashboardExercisesViewModel

    private lateinit var fetchState: MutableLiveData<FetchState>
    private lateinit var exercisesLiveData : MutableLiveData<List<ExerciseEntity>>
    private lateinit var errorLiveData : MutableLiveData<Throwable>

    @Before
    fun setUp() {

        fetchState = MutableLiveData()
        exercisesLiveData = MutableLiveData()
        errorLiveData = MutableLiveData()

        programLoader = FakeExerciseCacheLoader()
        viewModel = DefaultDashboardExercisesViewModel(fetchState,
            exercisesLiveData, errorLiveData, programLoader)

    }

    @Test
    fun loadExercises_success_exerciseLoaderCalled() {
        programLoader.error = false
        viewModel.loadExercises()
        assertThat(programLoader.called, CoreMatchers.`is`(true))
    }

    @Test
    fun loadExercises_success_fetchStateUpdatedToLoaded() {

        viewModel.loadExercises()

        assertThat(fetchState.value, CoreMatchers.`is`(FetchState.LOADED))
    }

    @Test
    fun loadExercises_error_fetchStateUpdatedToError() {

        programLoader.error = true
        viewModel.loadExercises()

        assertThat(fetchState.value, CoreMatchers.`is`(FetchState.ERROR))
    }

    @Test
    fun loadExercises_success_liveDataHasExercises() {

        programLoader.error = false
        viewModel.loadExercises()

        assertThat(exercisesLiveData.value, CoreMatchers.notNullValue())
    }

    @Test
    fun loadExercises_error_liveDataExercisesIsNull() {

        programLoader.error = true
        viewModel.loadExercises()

        assertThat(exercisesLiveData.value, CoreMatchers.nullValue())
    }

}

class FakeExerciseCacheLoader : ExercisesCacheLoader {

    var error = false
    var called = false

    override fun loadExercises(): Single<LoadedExercisesResult> {
        called = true

        return if (error)
            Single.error(RuntimeException())
        else
            Single.just(LoadedExercisesResult(ArrayList(), ArrayList()))
    }
}