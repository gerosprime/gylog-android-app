package com.gerosprime.gylog


import android.content.Intent
import android.view.View
import android.view.ViewGroup
import androidx.test.espresso.intent.Intents
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import androidx.test.rule.ActivityTestRule
import androidx.test.rule.GrantPermissionRule
import com.gerosprime.gylog.robots.addProgramScreen
import com.gerosprime.gylog.robots.addWorkoutDialog
import com.gerosprime.gylog.robots.dashProgramScreen
import com.gerosprime.gylog.robots.mainScreen
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.TypeSafeMatcher
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@LargeTest
@RunWith(AndroidJUnit4::class)
class AddProgramScreenTest {

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
    fun addNewProgram_success_newProgramInDashboard() {

        mActivityTestRule.launchActivity(Intent())

        mainScreen {
            clickDashboardPrograms()
            clickAdd()
        }

        addProgramScreen {
            programName("New Program Name")
            description("New Program Description")
            clickAddWorkout()
        }

        addWorkoutDialog {
            name("Sample workout")
            descriptiuon("Sample description")
            create()
        }

        addProgramScreen {
            addOrEditExercisesAtWorkout(0)
        }



        dashProgramScreen {

        }

    }

    private fun childAtPosition(
        parentMatcher: Matcher<View>, position: Int
    ): Matcher<View> {

        return object : TypeSafeMatcher<View>() {
            override fun describeTo(description: Description) {
                description.appendText("Child at position $position in parent ")
                parentMatcher.describeTo(description)
            }

            public override fun matchesSafely(view: View): Boolean {
                val parent = view.parent
                return parent is ViewGroup && parentMatcher.matches(parent)
                        && view == parent.getChildAt(position)
            }
        }
    }
}
