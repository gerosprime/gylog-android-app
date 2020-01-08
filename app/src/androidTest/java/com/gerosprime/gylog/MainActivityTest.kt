package com.gerosprime.gylog

import android.content.Intent
import androidx.test.espresso.intent.Intents
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import androidx.test.rule.ActivityTestRule
import androidx.test.rule.GrantPermissionRule
import com.gerosprime.gylog.robots.MainScreenRobot
import com.gerosprime.gylog.robots.mainScreen
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@LargeTest
@RunWith(AndroidJUnit4::class)
class MainActivityTest {


    @get:Rule
    @JvmField
    val mActivityTestRule = ActivityTestRule(MainActivity::class.java)

    @Rule
    @JvmField
    var mGrantPermissionRule =
        GrantPermissionRule.grant(
            "android.permission.READ_EXTERNAL_STORAGE"
        )

    @Before
    fun setUp() {
        Intents.init()
    }

    @After
    fun release() {
        Intents.release()
    }

    @Test
    fun openDashboardPrograms_clickFloatingButton_programAddScreenShown() {
        mActivityTestRule.launchActivity(Intent())
        mainScreen {
            clickDashboardPrograms()
            clickAdd()
            isProgramEditLaunched()
        }
    }

    @Test
    fun openDashboardExercises_clickFloatingButton_exerciseFormScreenShown() {
        mActivityTestRule.launchActivity(Intent())
        mainScreen {
            clickDashboardExercises()
            clickAdd()
            isExerciseEditLaunched()
        }
    }



}