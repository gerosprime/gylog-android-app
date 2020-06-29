package com.gerosprime.gylog.ui.workouts.session.adapters.sets

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.gerosprime.gylog.base.OnItemClickListener
import com.gerosprime.gylog.models.exercises.performedsets.PerformedSetEntity
import com.gerosprime.gylog.ui.workouts.R
import com.gerosprime.gylog.ui.workouts.session.adapters.sets.PerformedSetAdapter.ViewType.ADD_BUTTON
import com.gerosprime.gylog.ui.workouts.session.adapters.sets.PerformedSetAdapter.ViewType.EXERCISE


internal class PerformedSetAdapter(
    var performedSets: ArrayList<PerformedSetEntity>,
    private var setItemClick: OnItemClickListener<Int>,
    private var addItemClick: AddSetClickListener,
    private var removeSetClick: OnItemClickListener<Int>,
    private var unRemoveSetClick: OnItemClickListener<Int>
)
    : RecyclerView.Adapter<SessionSetViewHolder>() {

    private var layoutInflater : LayoutInflater? = null

    private object ViewType {
        const val EXERCISE = 0
        const val ADD_BUTTON = 1
    }

    interface AddSetClickListener {
        fun onAddSetClicked()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SessionSetViewHolder {

        if (layoutInflater == null)
            layoutInflater = LayoutInflater.from(parent.context)

        return if (viewType == EXERCISE) {

            val inflated = layoutInflater!!.inflate(R.layout.viewholder_workout_session_sets,
                parent, false)
            PerformedSetViewHolder(inflated, setItemClick, removeSetClick, unRemoveSetClick)

        } else {

            val inflated = layoutInflater!!.inflate(R.layout.viewholder_workout_session_add_set,
                parent, false)
            inflated.setOnClickListener { addItemClick.onAddSetClicked() }
            AddSetViewHolder(inflated)

        }

    }

    override fun getItemViewType(position: Int): Int =
        if (position == performedSets.size) ADD_BUTTON
        else EXERCISE

    override fun getItemCount(): Int = performedSets.size + 1

    override fun onBindViewHolder(holder: SessionSetViewHolder, position: Int) {
        if (holder is PerformedSetViewHolder) {
            holder.set(performedSets[position], position)
        }
    }
}