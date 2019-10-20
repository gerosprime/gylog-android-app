package com.gerosprime.gylog.components.di.modules

import com.gerosprime.gylog.ui.exercises.add.WorkoutExerciseEditActivity
import com.gerosprime.gylog.ui.programs.add.ProgramsAddActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityBuilders {
    @ContributesAndroidInjector
    abstract fun contributeProgramsAddActivity() : ProgramsAddActivity

    @ContributesAndroidInjector
    abstract fun contributeWorkoutEditScreen() : WorkoutExerciseEditActivity

}