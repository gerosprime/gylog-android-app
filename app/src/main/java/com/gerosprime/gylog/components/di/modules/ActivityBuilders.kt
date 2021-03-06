package com.gerosprime.gylog.components.di.modules

import com.gerosprime.gylog.ui.exercises.add.ExerciseAddActivity
import com.gerosprime.gylog.ui.exercises.detail.ExerciseDetailActivity
import com.gerosprime.gylog.ui.exercises.templatesets.EditTemplateSetsActivity
import com.gerosprime.gylog.ui.exercises.templatesets.detail.TemplateSetEditActivity
import com.gerosprime.gylog.ui.programs.add.ProgramsAddActivity
import com.gerosprime.gylog.ui.programs.detail.ProgramDetailActivity
import com.gerosprime.gylog.ui.workouts.exercises.WorkoutExerciseEditActivity
import com.gerosprime.gylog.ui.workouts.session.WorkoutSessionActivity
import com.gerosprime.gylog.ui.you.YouFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityBuilders {
    @ContributesAndroidInjector
    abstract fun contributeProgramsAddActivity() : ProgramsAddActivity

    @ContributesAndroidInjector
    abstract fun contributeWorkoutEditScreen() : WorkoutExerciseEditActivity

    @ContributesAndroidInjector
    abstract fun contributeEditTemplateScreen() : EditTemplateSetsActivity

    @ContributesAndroidInjector
    abstract fun contributeTemplateEditScreen() : TemplateSetEditActivity

    @ContributesAndroidInjector
    abstract fun contributeProgramDetailActivity() : ProgramDetailActivity

    @ContributesAndroidInjector
    abstract fun contributeWorkoutSessionActivity() : WorkoutSessionActivity

    @ContributesAndroidInjector
    abstract fun contributeExerciseAddActivity() : ExerciseAddActivity

    @ContributesAndroidInjector
    abstract fun contributeExerciseDetailActivity() : ExerciseDetailActivity

    @ContributesAndroidInjector
    abstract fun contributeYouFragment() : YouFragment


}