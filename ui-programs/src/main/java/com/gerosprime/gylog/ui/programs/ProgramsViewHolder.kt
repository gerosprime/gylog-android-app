package com.gerosprime.gylog.ui.programs

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.gerosprime.gylog.base.OnItemClickListener
import com.gerosprime.gylog.models.programs.ProgramEntity

class ProgramsViewHolder(
    itemView: View,
    private var clickListener: OnItemClickListener<ProgramEntity>?
) : RecyclerView.ViewHolder(itemView) {

    var programImage : ImageView = itemView.findViewById(R.id.viewholder_program_tile_item_photo)
    var titleText : TextView = itemView.findViewById(R.id.viewholder_program_tile_item_name)

    lateinit var program : ProgramEntity
    private var programPosition : Int = -1

    init {
        itemView.setOnClickListener { clickListener!!.onItemClicked(program) }
    }

    fun set(program : ProgramEntity, position : Int) {
        this.program = program
        this.programPosition = position
        titleText.text = program.name

        if (program.imageUri.isNotEmpty())
            Glide.with(itemView).load(program.imageUri).into(programImage)
        // programImage.ima
    }


}