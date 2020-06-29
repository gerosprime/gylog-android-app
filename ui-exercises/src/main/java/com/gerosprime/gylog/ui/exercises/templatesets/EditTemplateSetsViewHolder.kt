package com.gerosprime.gylog.ui.exercises.templatesets

import android.content.res.Resources
import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.gerosprime.gylog.base.OnItemClickListener
import com.gerosprime.gylog.base.utils.TimeFormatUtil
import com.gerosprime.gylog.models.exercises.templatesets.TemplateSetEntity
import com.gerosprime.gylog.ui.exercises.R
import com.gerosprime.gylog.ui.exercises.databinding.ViewholderTemplateSetBinding

class EditTemplateSetsViewHolder(itemView : View,
                                 var itemClickListener : OnItemClickListener<Int>? = null)
    : RecyclerView.ViewHolder(itemView) {


    lateinit var entity : TemplateSetEntity
    var templatePosition : Int = -1

    private val binding = ViewholderTemplateSetBinding.bind(itemView)

    private val resources : Resources = itemView.resources

    init {
        itemView.setOnClickListener {
            itemClickListener!!.onItemClicked(templatePosition)
        }
    }


    fun set(entity: TemplateSetEntity, position : Int) {

        this.entity = entity
        this.templatePosition = position

        binding.apply {
            viewholderTemplateSetIndex.text =
                resources.getString(R.string.template_set_index_format, position + 1)

            if (entity.minReps != null && entity.reps != null) {
                viewholderTemplateSetMinMaxReps.text = resources.getString(
                    R.string.template_set_min_max_reps_format,
                    entity.minReps, entity.reps
                )
            } else if (entity.minReps != null) {
                viewholderTemplateSetMinMaxReps.text = resources.getString(
                    R.string.template_set_reps_format,
                    entity.minReps)
            } else if (entity.reps != null) {
                viewholderTemplateSetMinMaxReps.text = resources.getString(
                    R.string.template_set_reps_format,
                    entity.reps)
            } else {
                viewholderTemplateSetMinMaxReps.text = resources.getText(R.string.template_set_no_reps_range_set)
            }

            if (entity.weight != null && entity.weight!! > 0f) {
                viewholderTemplateSetWeight.text = resources.getString(
                    R.string.template_set_weight_format,
                    entity.weight, "KG"
                )
            } else {
                viewholderTemplateSetWeight.text = resources.getText(R.string.template_set_no_required_weight_range_set)
            }

            viewholderTemplateSetRest.text = resources.getString(R.string.template_set_rest_format,
                TimeFormatUtil.secondsToString(entity.restTimeSeconds.toLong()))

        }


    }



}