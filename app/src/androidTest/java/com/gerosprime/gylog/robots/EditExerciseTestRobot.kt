package com.gerosprime.gylog.robots

import androidx.test.espresso.Espresso
import androidx.test.espresso.ViewInteraction
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.withId
import com.gerosprime.gylog.R


fun editExerciseScreen(func: EditExerciseTestRobot.() -> Unit) = EditExerciseTestRobot().apply { func() }

class EditExerciseTestRobot : BaseTestRobot() {

    fun name(name : String) : ViewInteraction {
        return inputText(R.id.activity_exercise_add_edit_name, name)
    }

    fun description(desc : String) : ViewInteraction {
        return inputText(R.id.activity_exercise_add_edit_description, desc)
    }

    fun directions(directions : String) : ViewInteraction {
        return inputText(R.id.activity_exercise_add_edit_directions, directions)
    }

    fun clickAbs() : ViewInteraction {
        return Espresso.onView(withId(R.id.activity_exercise_add_muscle_abs))
            .perform(click())
    }

    fun clickTriceps() : ViewInteraction {
        return Espresso.onView(withId(R.id.activity_exercise_add_muscle_triceps))
            .perform(click())
    }

    fun clickBiceps() : ViewInteraction {
        return Espresso.onView(withId(R.id.activity_exercise_add_muscle_biceps))
            .perform(click())
    }

    fun clickChest() : ViewInteraction {
        return Espresso.onView(withId(R.id.activity_exercise_add_muscle_chest))
            .perform(click())
    }

    fun clickChestUpper() : ViewInteraction {
        return Espresso.onView(withId(R.id.activity_exercise_add_muscle_chest_upper))
            .perform(click())
    }

    fun clickChestLower() : ViewInteraction {
        return Espresso.onView(withId(R.id.activity_exercise_add_muscle_chest_lower))
            .perform(click())
    }

    fun clickBackUpper() : ViewInteraction {
        return Espresso.onView(withId(R.id.activity_exercise_add_muscle_back_upper))
            .perform(click())
    }

    fun clickBackLower() : ViewInteraction {
        return Espresso.onView(withId(R.id.activity_exercise_add_muscle_back_lower))
            .perform(click())
    }

    fun clickForearms() : ViewInteraction {
        return Espresso.onView(withId(R.id.activity_exercise_add_muscle_forearms))
            .perform(click())
    }

    fun clickShoulderSide() : ViewInteraction {
        return Espresso.onView(withId(R.id.activity_exercise_add_muscle_shoulder_side))
            .perform(click())
    }

    fun clickShoulderFront() : ViewInteraction {
        return Espresso.onView(withId(R.id.activity_exercise_add_muscle_shoulder_front))
            .perform(click())
    }

    fun clickShoulderBack() : ViewInteraction {
        return Espresso.onView(withId(R.id.activity_exercise_add_muscle_shoulder_back))
            .perform(click())
    }

    fun clickMuscleTraps() : ViewInteraction {
        return Espresso.onView(withId(R.id.activity_exercise_add_muscle_traps))
            .perform(click())
    }

    fun clickQuads() : ViewInteraction {
        return Espresso.onView(withId(R.id.activity_exercise_add_muscle_quads))
            .perform(click())
    }

    fun clickCalves() : ViewInteraction {
        return Espresso.onView(withId(R.id.activity_exercise_add_muscle_calves))
            .perform(click())
    }

    fun clickCancel() : ViewInteraction {
        return click(android.R.id.home)
    }

    fun clickSave() : ViewInteraction {
        return click(R.id.activity_workout_exercise_edit_save)
    }


}