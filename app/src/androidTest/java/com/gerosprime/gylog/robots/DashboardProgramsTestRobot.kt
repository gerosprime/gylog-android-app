package com.gerosprime.gylog.robots

import androidx.test.espresso.ViewInteraction
import com.gerosprime.gylog.R
import com.gerosprime.gylog.ui.programs.ProgramsViewHolder


fun dashProgramScreen(func: DashboardProgramsTestRobot.() -> Unit) = DashboardProgramsTestRobot().apply { func() }

class DashboardProgramsTestRobot : BaseTestRobot() {

    fun clickUserDashboardProgram(position : Int) : ViewInteraction {
        return recyclerViewItemClick<ProgramsViewHolder>(R.id.fragment_dashboard_userprograms,
            position)
    }

}