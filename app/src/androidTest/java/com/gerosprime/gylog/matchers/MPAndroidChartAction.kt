package com.gerosprime.gylog.matchers

import android.view.View
import androidx.test.espresso.UiController
import androidx.test.espresso.ViewAction
import com.github.mikephil.charting.charts.LineChart
import org.hamcrest.Matcher

class MPAndroidChartAction : ViewAction {
    override fun getDescription(): String {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getConstraints(): Matcher<View> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun perform(uiController: UiController?, view: View?) {
        val chart : LineChart
    }
}