package com.gerosprime.gylog.base.utils

import org.hamcrest.CoreMatchers
import org.junit.Assert.assertThat
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class TimeFormatUtilTest {


    @Test
    fun secondsToString_120seconds_2minutesString() {

        val format = TimeFormatUtil.secondsToString(120)

        assertThat(format, CoreMatchers.`is`("02:00"))
    }

    @Test(expected = IllegalArgumentException::class)
    fun secondsToString_NegativeSeconds_exceptionThrown() {
        TimeFormatUtil.secondsToString(-1)
    }

    @Test
    fun secondsToString_10seconds_10secondsString() {

        val format = TimeFormatUtil.secondsToString(10)

        assertThat(format, CoreMatchers.`is`("00:10"))
    }

    @Test
    fun secondsToString_999seconds_16minutes39secondsString() {

        val format = TimeFormatUtil.secondsToString(999)

        assertThat(format, CoreMatchers.`is`("16:39"))
    }

    @Test
    fun secondsToString_0seconds_16minutes39secondsstring() {

        val format = TimeFormatUtil.secondsToString(999)

        assertThat(format, CoreMatchers.`is`("16:39"))
    }

}