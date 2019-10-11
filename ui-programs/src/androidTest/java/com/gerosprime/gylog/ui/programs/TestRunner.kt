package com.gerosprime.gylog.ui.programs

import android.app.Application
import android.content.Context
import androidx.test.runner.AndroidJUnitRunner

class TestRunner : AndroidJUnitRunner() {
    override fun newApplication(
        cl: ClassLoader?,
        className: String?,
        context: Context?
    ): Application {
        return super.newApplication(cl, TestUiProgramsApplication::class.java.name, context)
    }
}