package com.gerosprime.gylog.ui.programs.add

import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Before
import org.junit.Rule
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ProgramsAddActivityTest {

    @get:Rule
    val activityRule : ActivityScenarioRule<ProgramsAddActivity>
            = ActivityScenarioRule(ProgramsAddActivity::class.java)

    @Before
    fun setUp() {

    }

}