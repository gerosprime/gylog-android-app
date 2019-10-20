package com.gerosprime.gylog.ui.exercises.add

import androidx.lifecycle.MutableLiveData
import com.gerosprime.gylog.base.FetchState
import com.gerosprime.gylog.models.exercises.ExerciseEntity
import com.gerosprime.gylog.models.workouts.edit.WorkoutExerciseSetCacheResult
import com.gerosprime.gylog.models.workouts.edit.WorkoutExerciseEditLoadResult


interface WorkoutExerciseEditViewModel {

    var exercisesMutableLiveData : MutableLiveData<WorkoutExerciseEditLoadResult>
    var fetchStateMutableLiveData : MutableLiveData<FetchState>
    var exerciseCacheMutableLiveData : MutableLiveData<WorkoutExerciseSetCacheResult>

    fun loadExercises(workoutIndex: Int)
    fun addExercisesIntoWorkout(workoutIndex : Int,
                                selectedExercises : List<ExerciseEntity>)

}