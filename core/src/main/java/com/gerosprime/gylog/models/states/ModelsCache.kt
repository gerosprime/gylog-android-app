package com.gerosprime.gylog.models.states

import com.gerosprime.gylog.models.body.fat.BodyFatEntity
import com.gerosprime.gylog.models.body.weight.BodyWeightEntity
import com.gerosprime.gylog.models.exercises.ExerciseEntity
import com.gerosprime.gylog.models.exercises.performed.ExercisePerformedEntity
import com.gerosprime.gylog.models.exercises.performedsets.PerformedSetEntity
import com.gerosprime.gylog.models.exercises.templates.ExerciseTemplateEntity
import com.gerosprime.gylog.models.exercises.templatesets.TemplateSetEntity
import com.gerosprime.gylog.models.programs.ProgramEntity
import com.gerosprime.gylog.models.workouts.WorkoutEntity

class ModelsCache (val programsMap : MutableMap<Long, ProgramEntity>,
                   val programs : ArrayList<ProgramEntity>,
                   val workouts : ArrayList<WorkoutEntity>,
                   val workoutsMap : MutableMap<Long, WorkoutEntity>,
                   var exercisesMap : MutableMap<Long, ExerciseEntity>,
                   var exercisesList : ArrayList<ExerciseEntity>,
                   val templateExercisesMap : MutableMap<Long, ExerciseTemplateEntity>,
                   val templateExercises : ArrayList<ExerciseTemplateEntity>,
                   val templateSets: ArrayList<TemplateSetEntity>,
                   val templateSetsMap: MutableMap<Long, TemplateSetEntity>,
                   val performedExercisesMap : MutableMap<Long, ExercisePerformedEntity>,
                   val performedExercises : ArrayList<ExercisePerformedEntity>,
                   val performedSets : ArrayList<PerformedSetEntity>,
                   val performedSetsMap : MutableMap<Long, PerformedSetEntity>,
                   val bodyWeightMap : MutableMap<Long, BodyWeightEntity>,
                   val bodyFatMap : MutableMap<Long, BodyFatEntity>) {

    fun clear() {
        programsMap.clear()
        programs.clear()
        workouts.clear()
        workoutsMap.clear()
        exercisesMap.clear()
        exercisesList.clear()
        templateExercisesMap.clear()
        templateExercises.clear()
        templateSets.clear()
        templateSetsMap.clear()
        performedExercises.clear()
        performedSets.clear()
        bodyWeightMap.clear()
        bodyFatMap.clear()
    }

}