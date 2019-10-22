package com.gerosprime.gylog.ui.exercises.templatesets

import android.content.res.Resources
import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.gerosprime.gylog.base.OnItemClickListener
import com.gerosprime.gylog.base.utils.TimeFormatUtil
import com.gerosprime.gylog.models.exercises.templatesets.TemplateSetEntity
import com.gerosprime.gylog.ui.exercises.R

class EditTemplateSetsViewHolder(itemView : View,
                                 var itemClickListener : OnItemClickListener<Int>? = null)
    : RecyclerView.ViewHolder(itemView) {


    lateinit var entity : TemplateSetEntity
    var templatePosition : Int = -1

    private val setIndexTextView : TextView
            = itemView.findViewById(R.id.viewholder_template_set_index)
    private val minMaxRepsTextView : TextView
            = itemView.findViewById(R.id.viewholder_template_set_min_max_reps)
    private val weightTextView : TextView
            = itemView.findViewById(R.id.viewholder_template_set_weight)
    private val restTimeTextView : TextView
            = itemView.findViewById(R.id.viewholder_template_set_rest)

    private val resources : Resources = itemView.resources

    init {
        itemView.setOnClickListener {
            itemClickListener!!.onItemClicked(templatePosition)
        }
    }


    fun set(entity: TemplateSetEntity, position : Int) {
        this.entity = entity
        this.templatePosition = position

        setIndexTextView.text = resources.getString(R.string.template_set_index_format,
            position + 1)
        minMaxRepsTextView.text = resources.getString(R.string.template_set_min_max_reps_format,
            entity.minReps, entity.reps)

        weightTextView.text = resources.getString(R.string.template_set_weight_format,
            entity.weight, "KG")

        restTimeTextView.text = resources.getString(R.string.template_set_rest_format,
            TimeFormatUtil.secondsToString(entity.restTimeSeconds.toLong()))

    }



}