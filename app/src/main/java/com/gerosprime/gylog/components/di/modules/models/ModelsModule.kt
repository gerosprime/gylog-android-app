package com.gerosprime.gylog.components.di.modules.models

import dagger.Module

@Module(includes = [CacheModule::class, ProgramsModelModule::class,
                    WorkoutModelModule::class, WorkoutSessionModelModule::class,
                        DatabaseModule::class,
                    ExercisesModelModule::class, BodyWeightModule::class])
class ModelsModule