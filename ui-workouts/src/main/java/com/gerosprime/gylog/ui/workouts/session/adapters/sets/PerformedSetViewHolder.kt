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

internal class PerformedSetViewHolder(
    itemView: View,
    private val itemClick: OnItemClickListener<Int>,
    private val removeItemClick: OnItemClickListener<Int>,
    private val unRemoveItemClick: OnItemClickListener<Int>

) : SessionSetViewHolder(itemView) {

    private val content : View = itemView.findViewById(R.id.viewholder_workout_session_sets_content)
    private val deletedContent : View = itemView.findViewById(R.id.viewholder_workout_session_deleted_container)
    private val unDeletedContent : View = itemView.findViewById(R.id.viewholder_workout_session_sets_undeleted_content)

    private val imageCheck : ImageView = itemView.findViewById(R.id.viewholder_workout_session_sets_check)

    private val positionTextView : TextView
            = itemView.findViewById(R.id.viewholder_workout_session_sets_position)
    private val weightTextView : TextView
            = itemView.findViewById(R.id.viewholder_workout_session_sets_weight)
    private val repsTextView : TextView
            = itemView.findViewById(R.id.viewholder_workout_session_sets_reps)
    private val moreButton : ImageButton
            = itemView.findViewById(R.id.viewholder_workout_session_sets_more)

    private val previousWeightTextView : TextView
            = itemView.findViewById(R.id.viewholder_workout_session_sets_weight_previous)
    private val previousRepsTextView : TextView
            = itemView.findViewById(R.id.viewholder_workout_session_sets_reps_previous)

    private val resources = itemView.resources

    private lateinit var performedSet: PerformedSetEntity
    private var performedSetIndex : Int = -1

    init {
        moreButton.setOnClickListener {
            val popupMenu = PopupMenu(itemView.context, moreButton)

            popupMenu.inflate(R.menu.menu_viewholder_performed_set_menu)

            val menu = popupMenu.menu
            val removeMenuItem = menu.findItem(R.id.menu_viewholder_performed_set_remove)
            removeMenuItem.isVisible = !performedSet.flagRemoved
            val restoreMenuItem = menu.findItem(R.id.menu_viewholder_performed_set_restore)
            restoreMenuItem.isVisible = performedSet.flagRemoved

            popupMenu.setOnMenuItemClickListener { menuClicked(it) }
            popupMenu.show()
        }

        content.setOnClickListener {
            itemClicked(it)
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

        positionTextView.text = resources
            .getString(R.string.workout_session_set_number_format, position + 1)

        when (performedSet.flagRemoved) {
            true -> {
                deletedContent.visibility = View.VISIBLE
                unDeletedContent.visibility = View.INVISIBLE
            }
            else -> {
                deletedContent.visibility = View.INVISIBLE
                unDeletedContent.visibility = View.VISIBLE
            }
        }


        when {
            (performedSet.weight != null || performedSet.reps != null) ->
                imageCheck.setImageResource(R.drawable.ic_check_circle_green_24dp)
            else -> {
                imageCheck.setImageResource(R.drawable.ic_radio_button_unchecked_green_24dp)
            }
        }

        when {
            performedSet.weight != null ->
                weightTextView.text = resources.getString(R.string.weight_format,
                performedSet.weight)
            else -> weightTextView.text = "--"
        }

        when {
            performedSet.reps != null ->
                repsTextView.text = resources.getQuantityString(R.plurals.reps_format,
                performedSet.reps!!, performedSet.reps!!)
            else -> repsTextView.text = "--"
        }

        when {
            performedSet.previousWeight != null ->
                previousWeightTextView.text = resources.getString(R.string.weight_format,
                performedSet.previousWeight)
            performedSet.initialWeight != null ->
                previousWeightTextView.text = resources.getString(R.string.weight_format,
                performedSet.initialWeight)
            else -> previousWeightTextView.text = ""
        }

        when {
            performedSet.previousReps != null ->
                previousRepsTextView.text = resources.getQuantityString(R.plurals.reps_format,
                    performedSet.previousReps!!, performedSet.previousReps!!)
            performedSet.initialReps != null ->
                previousRepsTextView.text = resources.getQuantityString(R.plurals.reps_format,
                    performedSet.initialReps!!, performedSet.initialReps!!)

            else -> previousRepsTextView.text = ""
        }


    }


}