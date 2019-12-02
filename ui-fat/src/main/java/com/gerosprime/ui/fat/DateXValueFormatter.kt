package com.gerosprime.ui.fat

import com.gerosprime.gylog.models.body.fat.BodyFatEntity
import com.gerosprime.gylog.models.body.weight.BodyWeightEntity
import com.github.mikephil.charting.components.AxisBase
import com.github.mikephil.charting.formatter.ValueFormatter
import java.text.DateFormat
import java.text.SimpleDateFormat

class DateXValueFormatter(
    private val dateFormat: SimpleDateFormat,
    private val points : ArrayList<BodyFatEntity>)
    : ValueFormatter() {

    override fun getAxisLabel(value: Float, axis: AxisBase?): String {
        return dateFormat.format(points[value.toInt() - 1].dateLogged)
    }
}