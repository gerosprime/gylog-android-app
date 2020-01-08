package com.gerosprime.gylog.robots

import androidx.test.espresso.Espresso
import androidx.test.espresso.ViewInteraction
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.matcher.RootMatchers
import androidx.test.espresso.matcher.RootMatchers.*
import androidx.test.espresso.matcher.ViewMatchers
import com.gerosprime.gylog.R


fun addWorkoutDialog(func: AddWorkoutDialogTestRobot.() -> Unit)
        = AddWorkoutDialogTestRobot().apply { func() }

class AddWorkoutDialogTestRobot : BaseTestRobot() {

    fun name(workoutName : String) : ViewInteraction {
        return Espresso.onView(
            ViewMatchers.withId(R.id.fragment_add_workout_dialog_name_edit))
            .inRoot(isDialog()).perform(ViewActions.typeText(workoutName))
    }

    fun descriptiuon(description : String) : ViewInteraction {
        return Espresso.onView(
            ViewMatchers.withId(R.id.fragment_add_workout_dialog_description_edit))
            .inRoot(isDialog()).perform(ViewActions.typeText(description))
    }

    fun create() :  ViewInteraction {
        return Espresso.onView(ViewMatchers.withText(R.string.create)).perform(ViewActions.click())
    }

    fun cancel() : ViewInteraction {
        return Espresso.onView(ViewMatchers.withText(R.string.cancel)).perform(ViewActions.click())
    }

}