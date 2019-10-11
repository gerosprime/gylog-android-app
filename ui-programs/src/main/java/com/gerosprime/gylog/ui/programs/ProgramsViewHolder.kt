package com.gerosprime.gylog.ui.programs

import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.gerosprime.gylog.models.programs.ProgramEntity

class ProgramsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    var programImage : ImageView
    var titleText : TextView

    lateinit var program : ProgramEntity

    init {
        programImage = itemView.findViewById(R.id.viewholder_program_tile_item_photo)
        titleText = itemView.findViewById(R.id.viewholder_program_tile_item_name)
    }

    fun set(program : ProgramEntity) {
        this.program = program

        titleText.text = program.name
        // programImage.ima
    }


}