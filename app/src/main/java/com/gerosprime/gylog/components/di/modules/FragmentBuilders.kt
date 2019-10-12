package com.gerosprime.gylog.components.di.modules

import com.gerosprime.gylog.ui.exercises.dashboard.DashboardExercisesFragment
import com.gerosprime.gylog.ui.programs.ProgramsDashboardFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector


@Module
abstract class FragmentBuilders {

    @ContributesAndroidInjector
    abstract fun contributeDashboardPrograms() : ProgramsDashboardFragment
    @ContributesAndroidInjector
    abstract fun contributeDashboardExercisesFragment() : DashboardExercisesFragment
}