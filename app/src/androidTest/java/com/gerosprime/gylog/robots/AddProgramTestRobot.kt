package com.gerosprime.gylog.robots

import androidx.test.espresso.ViewInteraction
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.contrib.RecyclerViewActions
import com.gerosprime.gylog.R
import com.gerosprime.gylog.ui.programs.add.workouts.ProgramWorkoutsViewHolder


fun addProgramScreen(func: AddProgramTestRobot.() -> Unit) = AddProgramTestRobot().apply { func() }

class AddProgramTestRobot : BaseTestRobot() {

    fun programName(name : String) : ViewInteraction {
        return inputText(R.id.fragment_add_programs_name_edittext, name)
    }

    fun description(desc : String) : ViewInteraction {
        return inputText(R.id.fragment_add_programs_description_edittext, desc)
    }

    fun clickSaveProgram() : ViewInteraction {
        return click(R.id.activity_add_program_save)
    }

    fun clickAddWorkout() : ViewInteraction {
        return click(R.id.fragment_add_programs_add_workout)
    }

    fun addOrEditExercisesAtWorkout(position : Int) : ViewInteraction {
        return recyclerViewItemScrollTo<ProgramWorkoutsViewHolder>(R.id.fragment_add_programs_workouts, position)
            .perform(RecyclerViewActions
                .actionOnItemAtPosition<ProgramWorkoutsViewHolder>(position,
                    ViewActions.click()))
    }

}