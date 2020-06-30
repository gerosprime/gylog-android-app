package com.gerosprime.gylog.ui.programs

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.gerosprime.gylog.base.OnItemClickListener
import com.gerosprime.gylog.models.programs.ProgramEntity
import com.gerosprime.gylog.ui.programs.databinding.ViewholderProgramTileItemBinding

class ProgramsViewHolder(
    itemView: View,
    private var clickListener: OnItemClickListener<ProgramItemClick>?
) : RecyclerView.ViewHolder(itemView) {


    private val binding = ViewholderProgramTileItemBinding.bind(itemView)

    lateinit var program : ProgramEntity
    private var programPosition : Int = -1

    init {
        itemView.setOnClickListener { clickListener!!.onItemClicked(ProgramItemClick(programPosition, program)) }
    }

    fun set(program : ProgramEntity, position : Int) {
        this.program = program
        this.programPosition = position

        binding.apply {
            binding.viewholderProgramTileItemName.text = program.name

            if (program.imageUri.isNotEmpty())
                Glide.with(itemView).load(program.imageUri)
                    .into(binding.viewholderProgramTileItemPhoto)
        }

    }


}