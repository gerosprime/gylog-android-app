package com.gerosprime.gylog.ui.workouts.session.adapters.exercises

import android.view.MenuItem
import android.view.View
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
import com.gerosprime.gylog.ui.workouts.databinding.ViewholderWorkoutSessionExercisesBinding
import com.gerosprime.gylog.ui.workouts.session.adapters.sets.PerformedSetAdapter


internal class PerformedExerciseViewHolder
    (itemView: View,
     private val exerciseClickListener: OnItemClickListener<WorkoutExerciseClick>,
     private val setClickListener: OnItemClickListener<PerformedSetClick>,
     private var addItemClick : OnItemClickListener<AddPerformSetClick>,
     private var removeItemClick : OnItemClickListener<RemovePerformSetClick>,
     private var unRemoveItemClick : OnItemClickListener<UnRemovePerformSetClick>)
    : RecyclerView.ViewHolder(itemView) {

    private val binding = ViewholderWorkoutSessionExercisesBinding.bind(itemView)

    private lateinit var performedExercise : ExercisePerformedEntity
    private var exercisePosition : Int = adapterPosition

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

        binding.apply {
            viewholderWorkoutSessionPerformedsets.adapter = PerformedSetAdapter(
                listOf(),
                internalSetClickListener, internalAddClickListener,
                internalRemoveSetClickListener, internalUnRemoveSetClickListener)

            viewholderWorkoutSessionPerformedsets.layoutManager = LinearLayoutManager(itemView.context,
                LinearLayoutManager.HORIZONTAL, false)

            root.setOnClickListener {
                itemClicked(it)
            }

            viewholderWorkoutSessionExerciseMore.setOnClickListener {
                val popupMenu = PopupMenu(itemView.context, viewholderWorkoutSessionExerciseMore)
                popupMenu.menuInflater.inflate(R.menu.menu_viewholder_performed_set_menu,
                    popupMenu.menu)
                popupMenu.setOnMenuItemClickListener { menuClicked(it) }
                popupMenu.show()
            }
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

        binding.apply {

            viewholderWorkoutSessionExerciseName.text = performedExercise.name

            viewholderWorkoutSessionExerciseName.setOnClickListener {
                internalExerciseClickListener.onItemClicked(position)
            }

            if (payloads.isEmpty())
                return

            val adapter = viewholderWorkoutSessionPerformedsets.adapter as PerformedSetAdapter
            when (payloads[0]) {
                is AddPerformedSetResult -> {

                    adapter.notifyItemInserted(adapter.itemCount)
                    viewholderWorkoutSessionPerformedsets.smoothScrollToPosition(adapter.itemCount - 1)
                }

                is ClearPerformedSetResult -> {
                    val payload = payloads[0] as ClearPerformedSetResult
                    adapter.notifyItemChanged(payload.setIndex)
                }
                is EditPerformedSetResult -> {
                    val payload = payloads[0] as EditPerformedSetResult
                    adapter.notifyItemChanged(payload.setIndex)
                }

                is RemoveWorkoutSessionSetResult -> {
                    val payload = payloads[0] as RemoveWorkoutSessionSetResult

                    if (payload.flagRemoved) {
                        adapter.notifyItemChanged(payload.setIndex)
                    } else {
                        adapter.notifyItemRemoved(payload.setIndex)
                        adapter.notifyItemRangeChanged(payload.setIndex, adapter.itemCount)
                    }
                }

                is UnflagRemovePerformedSetResult -> {
                    val payload = payloads[0] as UnflagRemovePerformedSetResult
                    adapter.notifyItemChanged(payload.setIndex)
                }
            }

        }

    }

    fun set(performedExercise : ExercisePerformedEntity, position : Int) {
        this.performedExercise = performedExercise
        this.exercisePosition = position

        binding.apply {

            viewholderWorkoutSessionExerciseName.text = performedExercise.name
            viewholderWorkoutSessionExerciseName.setOnClickListener { internalExerciseClickListener.onItemClicked(position) }

            val adapter = viewholderWorkoutSessionPerformedsets.adapter as PerformedSetAdapter
            adapter.performedSets = performedExercise.performedSets
            adapter.notifyItemRangeChanged(0, performedExercise.performedSets.size)

        }

    }

}