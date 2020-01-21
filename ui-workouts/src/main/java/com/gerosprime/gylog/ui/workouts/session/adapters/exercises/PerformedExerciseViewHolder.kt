package com.gerosprime.gylog.ui.workouts.session.adapters.exercises

import android.view.MenuItem
import android.view.View
import android.widget.TextView
import androidx.appcompat.widget.PopupMenu
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.gerosprime.gylog.base.OnItemClickListener
import com.gerosprime.gylog.models.exercises.performed.ExercisePerformedEntity
import com.gerosprime.gylog.models.workouts.runningsession.performedset.add.AddPerformedSetResult
import com.gerosprime.gylog.models.workouts.runningsession.performedset.edit.ClearPerformedSetResult
import com.gerosprime.gylog.models.workouts.runningsession.performedset.edit.EditPerformedSetResult
import com.gerosprime.gylog.models.workouts.runningsession.performedset.remove.RemoveWorkoutSessionSetResult
import com.gerosprime.gylog.models.workouts.runningsession.performedset.remove.UnflagRemovePerformedSetResult
import com.gerosprime.gylog.ui.workouts.R
import com.gerosprime.gylog.ui.workouts.session.adapters.sets.PerformedSetAdapter
import com.google.android.material.button.MaterialButton


internal class PerformedExerciseViewHolder
    (itemView: View,
     private val exerciseClickListener: OnItemClickListener<WorkoutExerciseClick>,
     private val setClickListener: OnItemClickListener<PerformedSetClick>,
     private var addItemClick : OnItemClickListener<AddPerformSetClick>,
     private var removeItemClick : OnItemClickListener<RemovePerformSetClick>,
     private var unRemoveItemClick : OnItemClickListener<UnRemovePerformSetClick>)
    : RecyclerView.ViewHolder(itemView) {

    private val nameTextView :TextView
            = itemView.findViewById(R.id.viewholder_workout_session_exercise_name)
    private val moreButton :MaterialButton
            = itemView.findViewById(R.id.viewholder_workout_session_exercise_more)
    private val setsRecyclerView :RecyclerView
            = itemView.findViewById(R.id.viewholder_workout_session_performedsets)

    private lateinit var performedExercise : ExercisePerformedEntity
    private var exercisePosition : Int = -1

    private val internalExerciseClickListener = object : OnItemClickListener<Int> {
        override fun onItemClicked(item: Int) {
            exerciseClickListener.onItemClicked(
                WorkoutExerciseClick(exercisePosition, performedExercise.exerciseId))
        }

    }

    private val internalSetClickListener = object : OnItemClickListener<Int> {

        override fun onItemClicked(item: Int) {
            setClickListener.onItemClicked(
                PerformedSetClick(exercisePosition, item,
                    performedExercise, performedExercise.performedSets[item]))
        }
    }

    private val internalAddClickListener = object : PerformedSetAdapter.AddSetClickListener {
        override fun onAddSetClicked() {
            addItemClick.onItemClicked(AddPerformSetClick(exercisePosition))
        }
    }

    private val internalRemoveSetClickListener = object : OnItemClickListener<Int> {

        override fun onItemClicked(item: Int) {
            removeItemClick.onItemClicked(
                RemovePerformSetClick(exercisePosition, item))
        }
    }
    private val internalUnRemoveSetClickListener = object : OnItemClickListener<Int> {

        override fun onItemClicked(item: Int) {
            unRemoveItemClick.onItemClicked(
                UnRemovePerformSetClick(exercisePosition, item))
        }
    }


    init {

        setsRecyclerView.layoutManager = LinearLayoutManager(itemView.context,
            LinearLayoutManager.HORIZONTAL, false)

        itemView.setOnClickListener {
            itemClicked(it)
        }

        moreButton.setOnClickListener {
            val popupMenu = PopupMenu(itemView.context, moreButton)
            popupMenu.menuInflater.inflate(R.menu.menu_viewholder_performed_set_menu,
                popupMenu.menu)
            popupMenu.setOnMenuItemClickListener { menuClicked(it) }
            popupMenu.show()
        }
    }

    private fun menuClicked(menuItem: MenuItem) : Boolean {

        return true
    }

    private fun itemClicked(view: View) {

    }

    fun update(performedExercise : ExercisePerformedEntity,
               position : Int,
               payloads: MutableList<Any>) {
        this.performedExercise = performedExercise
        this.exercisePosition = position
        nameTextView.text = performedExercise.name

        nameTextView.setOnClickListener { internalExerciseClickListener.onItemClicked(position) }

        if (payloads.isEmpty())
            return

        when (payloads[0]) {
            is AddPerformedSetResult -> {
                val adapter = setsRecyclerView.adapter
                adapter!!.notifyItemInserted(
                    adapter.itemCount)
                setsRecyclerView.smoothScrollToPosition(adapter.itemCount - 1)
            }

            is ClearPerformedSetResult -> {
                val adapter = setsRecyclerView.adapter
                val payload = payloads[0] as ClearPerformedSetResult
                adapter!!.notifyItemChanged(payload.setIndex)
            }
            is EditPerformedSetResult -> {
                val adapter = setsRecyclerView.adapter
                val payload = payloads[0] as EditPerformedSetResult
                adapter!!.notifyItemChanged(payload.setIndex)
            }

            is RemoveWorkoutSessionSetResult -> {
                val adapter = setsRecyclerView.adapter
                val payload = payloads[0] as RemoveWorkoutSessionSetResult

                if (payload.flagRemoved) {
                    adapter?.notifyItemChanged(payload.setIndex)
                } else {
                    adapter?.notifyItemRemoved(payload.setIndex)
                    adapter?.notifyItemRangeChanged(payload.setIndex, adapter.itemCount)
                }
            }

            is UnflagRemovePerformedSetResult -> {
                val adapter = setsRecyclerView.adapter
                val payload = payloads[0] as UnflagRemovePerformedSetResult
                adapter!!.notifyItemChanged(payload.setIndex)
            }
        }


    }

    fun set(performedExercise : ExercisePerformedEntity, position : Int) {
        this.performedExercise = performedExercise
        this.exercisePosition = position
        nameTextView.text = performedExercise.name

        nameTextView.setOnClickListener { internalExerciseClickListener.onItemClicked(position) }

        setsRecyclerView.adapter =
            PerformedSetAdapter(performedExercise.performedSets,
                internalSetClickListener, internalAddClickListener,
                internalRemoveSetClickListener, internalUnRemoveSetClickListener)

    }

}