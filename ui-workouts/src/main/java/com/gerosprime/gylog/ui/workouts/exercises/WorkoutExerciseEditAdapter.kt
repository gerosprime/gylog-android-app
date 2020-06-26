package com.gerosprime.gylog.ui.workouts.exercises

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.gerosprime.gylog.base.OnItemClickListener
import com.gerosprime.gylog.models.exercises.ExerciseEntity
import com.gerosprime.gylog.ui.workouts.R


class WorkoutExerciseEditAdapter(private val workoutExercises : MutableMap<Long, ExerciseEntity>,
                                 private val exercises : List<ExerciseEntity>,
                                 private var imageClickListener : OnItemClickListener<ExerciseEntity>? = null)
    : RecyclerView.Adapter<WorkoutExerciseEditViewHolder>() {


    fun selectedItems() : List<ExerciseEntity> {

        val items : ArrayList<ExerciseEntity> = arrayListOf()
        for (selectedItem in workoutExercises) {
            items.add(selectedItem.value)
        }
        return items
    }

    fun selectedItemsSize() = workoutExercises.size

    var layoutInflater : LayoutInflater? = null

    private var internalImageClickListener = object : OnItemClickListener<ExerciseEntity> {
        override fun onItemClicked(item: ExerciseEntity) {
            imageClickListener?.onItemClicked(item)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WorkoutExerciseEditViewHolder {

        if (layoutInflater == null)
            layoutInflater = LayoutInflater.from(parent.context)

        val inflate = layoutInflater!!.inflate(
            R.layout.viewholder_workout_exercise_add,
                                    parent, false)

        return WorkoutExerciseEditViewHolder(
            inflate,
            workoutExercises,
            internalImageClickListener
        )

    }

    override fun getItemCount(): Int = exercises.size

    override fun onBindViewHolder(holder: WorkoutExerciseEditViewHolder, position: Int) {

        val exerciseEntity = exercises[position];
        holder.set(exerciseEntity)
    }
}

