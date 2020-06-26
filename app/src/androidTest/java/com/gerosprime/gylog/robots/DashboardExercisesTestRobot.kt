package com.gerosprime.gylog.robots

import android.content.ComponentName
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.ViewInteraction
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.intent.Intents.intended
import androidx.test.espresso.intent.matcher.IntentMatchers
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.platform.app.InstrumentationRegistry.getInstrumentation
import com.gerosprime.gylog.R
import com.gerosprime.gylog.matchers.RecyclerViewMatcher
import com.gerosprime.gylog.models.muscle.MuscleEnum
import com.gerosprime.gylog.ui.exercises.dashboard.DashboardExercisesViewHolder
import com.gerosprime.gylog.ui.exercises.detail.ExerciseDetailActivity
import org.hamcrest.Matchers.not

fun dashboardExercises(func: DashboardExercisesTestRobot.() -> Unit) = DashboardExercisesTestRobot().apply { func() }

class DashboardExercisesTestRobot : BaseTestRobot() {

    fun clickExerciseAt(position : Int) : ViewInteraction {
        return recyclerViewItemClick<DashboardExercisesViewHolder>(R.id.dashboardExercises,
            position)
    }

    fun checkNameAt(position : Int, name : String) : ViewInteraction {

        return onView(RecyclerViewMatcher(R.id.dashboardExercises)
            .atPositionOnView(position, R.id.viewholder_dashboard_name))
            .check(matches(isDisplayed()))
            .check(matches(withText(name)))
    }

    fun checkExerciseNotInDashboard(position: Int,
                                    name : String) : ViewInteraction {
        return onView(RecyclerViewMatcher(R.id.dashboardExercises)
            .atPositionOnView(position, R.id.viewholder_dashboard_name))
            .check(matches(not(hasDescendant(withText(name)))))

    }

    fun checkTargetMuscleAt(position : Int, muscleEnum: MuscleEnum) : ViewInteraction {

        return onView(RecyclerViewMatcher(R.id.dashboardExercises)
            .atPositionOnView(position, R.id.viewholder_dashboard_muscles))
            .check(matches(isDisplayed()))
            .check(matches(hasDescendant(withText(muscleEnum.stringResId))))
    }

    fun scrollToItem(position: Int) : ViewInteraction {
        return recyclerViewItemScrollTo<DashboardExercisesViewHolder>(
            R.id.dashboardExercises, position)
    }

    fun isExerciseDetailOpened() {
        intended(
            IntentMatchers.hasComponent(
                ComponentName(
            getInstrumentation().targetContext,
            ExerciseDetailActivity::class.java)
            ))
    }

}