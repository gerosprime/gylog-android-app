package com.gerosprime.gylog.ui.programs.add.workouts

import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.gerosprime.gylog.base.OnItemClickListener
import com.gerosprime.gylog.models.workouts.WorkoutEntity
import com.gerosprime.gylog.ui.programs.R
import com.gerosprime.gylog.ui.programs.add.exercises.ExerciseExecutionAdapter
import com.gerosprime.gylog.ui.programs.add.exercises.ExerciseExecutionClicked

internal class ProgramWorkoutsViewHolder(itemView : View,
                                         private var exerciseWorkoutListener
                                            : OnItemClickListener<Int>? = null,
                                         private var exerciseClickListener
                                            : OnItemClickListener<ExerciseExecutionClicked>? = null)

    : RecyclerView.ViewHolder(itemView) {

    private var workoutEntity : WorkoutEntity? = null
    private var workoutIndex : Int = -1

    private var exercisesRecyclerView: RecyclerView
            = itemView.findViewById(R.id.viewholder_program_workouts_exercises)

    private var emptyTextView : TextView = itemView.findViewById(R.id.viewholder_program_workouts_empty)

    private var titleTextView : TextView = itemView.findViewById(R.id.viewholder_program_workouts_title)
    private var subTitleTextView : TextView = itemView.findViewById(R.id.viewholder_program_workouts_subtitle)
    private var editExerciseButton : Button = itemView.findViewById(R.id.viewholder_program_workouts_add_exercise)

    private var context = itemView.context

    init {

        editExerciseButton.setOnClickListener { exerciseWorkoutListener!!.onItemClicked(workoutIndex) }

        exercisesRecyclerView.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)

    }

    fun set(workoutEntity : WorkoutEntity, workoutIndex : Int) {
        this.workoutEntity = workoutEntity
        this.workoutIndex = workoutIndex

        // TODO Cache adapters

        titleTextView.text = workoutEntity.name
        subTitleTextView.text = context.getString(R.string.day_format, workoutEntity.day)

        val exercises = workoutEntity.exercises
        if (exercises.isNullOrEmpty()) {
            emptyTextView.visibility = View.VISIBLE
            exercisesRecyclerView.visibility = View.INVISIBLE
        } else {

            emptyTextView.visibility = View.INVISIBLE
            exercisesRecyclerView.visibility = View.VISIBLE
            exercisesRecyclerView.adapter = ExerciseExecutionAdapter(exercises, workoutIndex,
                exerciseClickListener)


        }


    }

}