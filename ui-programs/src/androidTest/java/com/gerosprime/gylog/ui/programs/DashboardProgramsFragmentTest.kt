package com.gerosprime.gylog.ui.programs

import android.os.SystemClock
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.gerosprime.gylog.models.programs.ProgramsLoader
import it.cosenonjaviste.daggermock.DaggerMockRule
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import javax.inject.Inject

@RunWith(AndroidJUnit4::class)
class DashboardProgramsFragmentTest {

//    @get:Rule
//    var rule : DaggerMockRule<TestUiComponent> = DaggerMockRule(TestUiComponent::class.java)

    @Inject
    lateinit var programLoader: ProgramsLoader

    @Before
    fun setUp() {
        DaggerTestUiComponent.create().inject(this)
    }

    @Test
    fun test() {
        launchFragmentInContainer<ProgramsDashboardFragment> ()
        SystemClock.sleep(20000)
    }



}