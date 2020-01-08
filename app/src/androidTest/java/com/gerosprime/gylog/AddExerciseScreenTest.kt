package com.gerosprime.gylog

import android.content.Intent
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.intent.Intents
import androidx.test.espresso.intent.matcher.IntentMatchers
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import androidx.test.rule.ActivityTestRule
import androidx.test.rule.GrantPermissionRule
import com.gerosprime.gylog.models.muscle.MuscleEnum
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
class AddExerciseScreenTest {

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
    fun addExercise_success_newExerciseInDashboard() {

        mActivityTestRule.launchActivity(Intent())

        mainScreen {
            clickDashboardExercises()
            clickAdd()
        }

        editExerciseScreen {
            name("Dumbbell Bench Press")
            description("Dumbbell version of bench press")
            directions("Sample direction")
            closeInputKeyboard()
            clickChest()
            clickTriceps()
            clickChestUpper()
            clickSave()
        }

        val lastItemIndex = mActivityTestRule
            .activity.findViewById<RecyclerView>(R.id.fragment_dashboard_exercises)
            .adapter!!.itemCount - 1

        dashboardExercises {
            scrollToItem(lastItemIndex)
            checkNameAt(lastItemIndex, "Dumbbell Bench Press")
            checkTargetMuscleAt(lastItemIndex, MuscleEnum.CHEST)
            checkTargetMuscleAt(lastItemIndex, MuscleEnum.TRICEPS)
            checkTargetMuscleAt(lastItemIndex, MuscleEnum.CHEST_UPPER)
        }

    }

    @Test
    fun addExercises_canceled_canceledExerciseNotInDashboard() {

        mActivityTestRule.launchActivity(Intent())

        mainScreen {
            clickDashboardExercises()
            clickAdd()
        }

        editExerciseScreen {
            name("Canceled Dumbbell Bench Press")
            description("Canceled  Dumbbell version of bench press")
            directions("Canceled Sample direction")
            closeInputKeyboard()
            pressBackButton()
        }

        val lastItemIndex = mActivityTestRule
            .activity.findViewById<RecyclerView>(R.id.fragment_dashboard_exercises)
            .adapter!!.itemCount - 1

        //
        if (lastItemIndex >= 0) {
            dashboardExercises {
                scrollToItem(lastItemIndex)
                checkExerciseNotInDashboard(lastItemIndex, "Canceled Dumbbell Bench Press")
            }
        }

    }


}
