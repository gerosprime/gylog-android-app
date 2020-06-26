package com.gerosprime.gylog

import android.content.Intent
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.intent.Intents
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import androidx.test.rule.ActivityTestRule
import androidx.test.rule.GrantPermissionRule
import com.gerosprime.gylog.robots.dashboardExercises
import com.gerosprime.gylog.robots.editExerciseScreen
import com.gerosprime.gylog.robots.mainScreen
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@LargeTest
@RunWith(AndroidJUnit4::class)
class DashboardExerciseScreenTest {


    @get:Rule
    @JvmField
    var mActivityTestRule = ActivityTestRule(MainActivity::class.java)

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
    fun exerciseItemCreatedAndClick_exerciseDetailOpened() {
        mActivityTestRule.launchActivity(Intent())

        mainScreen {
            clickDashboardExercises()
            clickAdd()
        }

        editExerciseScreen {
            name("Created Exercise")
            description("Created Exercise description")
            directions("Created Exercise direction")
            closeInputKeyboard()
            clickChest()
            clickTriceps()
            clickChestUpper()
            clickSave()
        }

        val lastItemIndex = mActivityTestRule
            .activity.findViewById<RecyclerView>(R.id.dashboardExercises)
            .adapter!!.itemCount - 1

        dashboardExercises {
            scrollToItem(lastItemIndex)
            clickExerciseAt(lastItemIndex)
            isExerciseDetailOpened()
        }

    }



}