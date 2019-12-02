package com.gerosprime.gylog.ui.programs.di

import com.gerosprime.gylog.ui.programs.ProgramsDashboardFragment
import com.gerosprime.gylog.ui.workouts.detail.WorkoutDetailDialogFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class FragmentBuilderModule {

    @ContributesAndroidInjector
    abstract fun contributeDashboardPrograms() : ProgramsDashboardFragment

    @ContributesAndroidInjector
    abstract fun contributeWorkoutDetailFragment() : WorkoutDetailDialogFragment
}