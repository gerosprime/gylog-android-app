package com.gerosprime.gylog.ui.workouts.session.adapters.sets

import android.view.MenuItem
import android.view.View
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.PopupMenu
import com.gerosprime.gylog.base.OnItemClickListener
import com.gerosprime.gylog.models.exercises.performedsets.PerformedSetEntity
import com.gerosprime.gylog.ui.workouts.R
import com.gerosprime.gylog.ui.workouts.databinding.ViewholderWorkoutSessionSetsBinding

internal class PerformedSetViewHolder(
    itemView: View,
    private val itemClick: OnItemClickListener<Int>,
    private val removeItemClick: OnItemClickListener<Int>,
    private val unRemoveItemClick: OnItemClickListener<Int>

) : SessionSetViewHolder(itemView) {

    private val binding = ViewholderWorkoutSessionSetsBinding.bind(itemView)

    private val resources = itemView.resources

    private lateinit var performedSet: PerformedSetEntity
    private var performedSetIndex : Int = -1

    init {

        binding.apply {
            viewholderWorkoutSessionSetsMore.setOnClickListener {
                val popupMenu = PopupMenu(itemView.context, viewholderWorkoutSessionSetsMore)

                popupMenu.inflate(R.menu.menu_viewholder_performed_set_menu)

                val menu = popupMenu.menu
                val removeMenuItem = menu.findItem(R.id.menu_viewholder_performed_set_remove)
                removeMenuItem.isVisible = !performedSet.flagRemoved
                val restoreMenuItem = menu.findItem(R.id.menu_viewholder_performed_set_restore)
                restoreMenuItem.isVisible = performedSet.flagRemoved

                popupMenu.setOnMenuItemClickListener { menuClicked(it) }
                popupMenu.show()
            }

            viewholderWorkoutSessionSetsContent.setOnClickListener {
                itemClicked(it)
            }

        }

    }

    private fun menuClicked(menuItem: MenuItem) : Boolean {
        when (menuItem.itemId) {
            R.id.menu_viewholder_performed_set_remove -> {
                removeItemClick.onItemClicked(performedSetIndex)
            }
            R.id.menu_viewholder_performed_set_restore -> {
                unRemoveItemClick.onItemClicked(performedSetIndex)
            }
        }
        return true
    }

    private fun itemClicked(view: View) {
        itemClick.onItemClicked(performedSetIndex)
    }

    fun set(performedSet: PerformedSetEntity, position : Int) {
        this.performedSet = performedSet
        this.performedSetIndex = position

        binding.apply {
            viewholderWorkoutSessionSetsPosition.text = resources
                .getString(R.string.workout_session_set_number_format, position + 1)

            viewholderWorkoutSessionDeletedContainer.visibility =
                if (performedSet.flagRemoved) View.VISIBLE else View.INVISIBLE

            viewholderWorkoutSessionSetsUndeletedContent.visibility =
                if (performedSet.flagRemoved) View.INVISIBLE else View.VISIBLE

            binding.apply {
                when {
                    (performedSet.weight != null || performedSet.reps != null) ->
                        viewholderWorkoutSessionSetsCheck.setImageResource(R.drawable.ic_check_circle_green_24dp)
                    else -> {
                        viewholderWorkoutSessionSetsCheck.setImageResource(R.drawable.ic_radio_button_unchecked_green_24dp)
                    }
                }

                when {
                    performedSet.weight != null ->
                        viewholderWorkoutSessionSetsWeight.text = resources.getString(R.string.weight_format,
                            performedSet.weight)
                    else -> viewholderWorkoutSessionSetsWeight.text = "--"
                }

                when {
                    performedSet.reps != null ->
                        viewholderWorkoutSessionSetsReps.text = resources.getQuantityString(R.plurals.reps_format,
                            performedSet.reps!!, performedSet.reps!!)
                    else -> viewholderWorkoutSessionSetsReps.text = "--"
                }

                when {
                    performedSet.previousWeight != null ->
                        viewholderWorkoutSessionSetsWeightPrevious.text = resources.getString(R.string.weight_format,
                            performedSet.previousWeight)
                    performedSet.initialWeight != null ->
                        viewholderWorkoutSessionSetsWeightPrevious.text = resources.getString(R.string.weight_format,
                            performedSet.initialWeight)
                    else -> viewholderWorkoutSessionSetsWeightPrevious.text = ""
                }

                when {
                    performedSet.previousReps != null ->
                        viewholderWorkoutSessionSetsRepsPrevious.text = resources.getQuantityString(R.plurals.reps_format,
                            performedSet.previousReps!!, performedSet.previousReps!!)
                    performedSet.initialReps != null ->
                        viewholderWorkoutSessionSetsRepsPrevious.text = resources.getQuantityString(R.plurals.reps_format,
                            performedSet.initialReps!!, performedSet.initialReps!!)

                    else -> viewholderWorkoutSessionSetsRepsPrevious.text = ""
                }
            }

        }

    }


}