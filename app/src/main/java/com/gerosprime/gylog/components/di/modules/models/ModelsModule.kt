package com.gerosprime.gylog.components.di.modules.models

import dagger.Module

@Module(includes = [CacheModule::class, ProgramsModelModule::class, WorkoutModelModule::class,
                    ExercisesModelModule::class])
class ModelsModule