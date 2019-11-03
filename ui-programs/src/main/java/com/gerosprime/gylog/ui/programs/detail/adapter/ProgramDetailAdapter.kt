package com.gerosprime.gylog.ui.programs.detail.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.gerosprime.gylog.base.OnItemClickListener
import com.gerosprime.gylog.models.programs.ProgramEntity
import com.gerosprime.gylog.ui.programs.R


class ProgramDetailAdapter(private val entity : ProgramEntity)

    : RecyclerView.Adapter<ProgramDetailViewHolder>() {

    private var layoutInflater : LayoutInflater? = null

    var itemClickListener: OnItemClickListener<Long>? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProgramDetailViewHolder {

        if (layoutInflater == null)
            layoutInflater = LayoutInflater.from(parent.context)

        val inflated = layoutInflater!!.inflate(R.layout.viewholder_program_detail_workout,
            parent, false)

        return ProgramDetailViewHolder(inflated, itemClickListener!!)

    }

    override fun getItemCount(): Int = entity.workouts!!.size

    override fun onBindViewHolder(holder: ProgramDetailViewHolder, position: Int) {
        holder.set(entity.workouts!![position], position)
    }
}