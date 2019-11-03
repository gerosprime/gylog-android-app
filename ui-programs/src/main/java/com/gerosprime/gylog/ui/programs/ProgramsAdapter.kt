package com.gerosprime.gylog.ui.programs

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.gerosprime.gylog.base.OnItemClickListener
import com.gerosprime.gylog.models.programs.ProgramEntity

class ProgramsAdapter(var items : List<ProgramEntity>) : RecyclerView.Adapter<ProgramsViewHolder>() {

    var layoutInflater : LayoutInflater? = null

    var clickListener : OnItemClickListener<ProgramEntity>? = null


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProgramsViewHolder {

        if (layoutInflater == null)
            layoutInflater = LayoutInflater.from(parent.context)

        var inflated = layoutInflater!!.inflate(R.layout.viewholder_program_tile_item,
            parent, false)
        return ProgramsViewHolder(inflated, clickListener)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: ProgramsViewHolder, position: Int) {
        holder.set(items[position], position)
    }
}