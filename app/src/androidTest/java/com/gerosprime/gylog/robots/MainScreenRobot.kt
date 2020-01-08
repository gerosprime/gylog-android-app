package com.gerosprime.gylog.robots

import android.content.ComponentName
import androidx.test.espresso.ViewInteraction
import androidx.test.espresso.intent.Intents.intended
import androidx.test.espresso.intent.matcher.IntentMatchers
import androidx.test.platform.app.InstrumentationRegistry.getInstrumentation
import com.gerosprime.gylog.R
import com.gerosprime.gylog.ui.exercises.add.ExerciseAddActivity
import com.gerosprime.gylog.ui.programs.add.ProgramsAddActivity


fun mainScreen(func: MainScreenRobot.() -> Unit) = MainScreenRobot().apply { func() }

class MainScreenRobot : BaseTestRobot() {


    fun clickAdd() : ViewInteraction {
        return click(R.id.activity_main_floating_action)
    }

    fun clickDashboardPrograms() : ViewInteraction {
        return click(R.id.navigation_programs)
    }

    fun clickDashboardYou() : ViewInteraction {
        return click(R.id.navigation_you)
    }

    fun clickDashboardExercises() : ViewInteraction {
        return click(R.id.navigation_exercises)
    }

    fun isProgramEditLaunched() {
        intended(IntentMatchers.hasComponent(ComponentName(
            getInstrumentation().targetContext,
            ProgramsAddActivity::class.java)))
    }

    fun isExerciseEditLaunched() {
        intended(IntentMatchers.hasComponent(ComponentName(
            getInstrumentation().targetContext,
            ExerciseAddActivity::class.java)))
    }

}