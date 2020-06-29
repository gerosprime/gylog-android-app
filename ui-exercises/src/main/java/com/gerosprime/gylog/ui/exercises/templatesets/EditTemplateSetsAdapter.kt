package com.gerosprime.gylog.ui.exercises.templatesets

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.gerosprime.gylog.base.OnItemClickListener
import com.gerosprime.gylog.models.exercises.templatesets.TemplateSetEntity
import com.gerosprime.gylog.ui.exercises.R

class EditTemplateSetsAdapter(var templates : List<TemplateSetEntity>) : RecyclerView.Adapter<EditTemplateSetsViewHolder>() {


    private var inflater : LayoutInflater? = null
    var itemClickListener : OnItemClickListener<Int>? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EditTemplateSetsViewHolder {

        if (inflater == null)
            inflater = LayoutInflater.from(parent.context)

        val inflated = inflater!!.inflate(R.layout.viewholder_template_set, parent, false)

        return EditTemplateSetsViewHolder(inflated, itemClickListener)

    }

    override fun getItemCount(): Int = templates.size

    override fun onBindViewHolder(holder: EditTemplateSetsViewHolder, position: Int) {
        holder.set(templates[position], position)
    }
}