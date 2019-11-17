package com.gerosprime.gylog.ui.programs

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import com.gerosprime.gylog.base.FetchState
import com.gerosprime.gylog.models.programs.LoadedProgramCacheResult
import com.gerosprime.gylog.models.programs.ProgramEntity
import com.gerosprime.gylog.models.programs.ProgramsCacheLoader
import io.reactivex.Single
import org.hamcrest.CoreMatchers
import org.hamcrest.CoreMatchers.`is`
import org.junit.Assert.assertThat
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import org.junit.runners.JUnit4


@RunWith(JUnit4::class)
class DefaultProgramsDashboardViewModelTest {

    @get:Rule
    val rule: TestRule = InstantTaskExecutorRule()

    lateinit var programLoader : FakeProgramCacheLoader

    lateinit var viewModel: ProgramsDashboardViewModel

    private lateinit var fetchState: MutableLiveData<FetchState>
    private lateinit var userProgramsLiveData : MutableLiveData<List<ProgramEntity>>
    private lateinit var builtInProgramsLiveData : MutableLiveData<List<ProgramEntity>>
    private lateinit var errorLiveData : MutableLiveData<Throwable>

    @Before
    fun setUp() {

        fetchState = MutableLiveData()
        userProgramsLiveData = MutableLiveData()
        builtInProgramsLiveData = MutableLiveData()
        errorLiveData = MutableLiveData()

        programLoader = FakeProgramCacheLoader()
        viewModel = DefaultProgramsDashboardViewModel(fetchState,
            userProgramsLiveData, builtInProgramsLiveData, errorLiveData, programLoader)

    }

    @Test
    fun loadUserPrograms_success_programsLoadedCalled() {
        programLoader.error = false
        viewModel.loadUserPrograms()
        assertThat(programLoader.called, `is`(true))
    }

    @Test
    fun loadUserPrograms_success_fetchStateUpdatedToLoaded() {

        viewModel.loadUserPrograms()

        assertThat(fetchState.value, `is`(FetchState.LOADED))
    }

    @Test
    fun loadUserPrograms_error_fetchStateUpdatedToError() {

        programLoader.error = true
        viewModel.loadUserPrograms()

        assertThat(fetchState.value, `is`(FetchState.ERROR))
    }

    @Test
    fun loadUserPrograms_success_liveDataHasUserList() {

        programLoader.error = false
        viewModel.loadUserPrograms()

        assertThat(userProgramsLiveData.value, CoreMatchers.notNullValue())
    }

    @Test
    fun loadUserPrograms_error_liveDataUserListIsNull() {

        programLoader.error = true
        viewModel.loadUserPrograms()

        assertThat(userProgramsLiveData.value, CoreMatchers.nullValue())
    }


}

class FakeProgramCacheLoader : ProgramsCacheLoader {

    var error : Boolean = false
    var called : Boolean = false

    override fun loadUserPrograms(): Single<LoadedProgramCacheResult> {
        called = true
        return if (error) {
            Single.error(RuntimeException())
        } else Single.just(LoadedProgramCacheResult(ArrayList(), ArrayList()))
    }




}
