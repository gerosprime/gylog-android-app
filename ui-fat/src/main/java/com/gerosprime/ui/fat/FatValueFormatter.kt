package com.gerosprime.ui.fat

import com.gerosprime.gylog.models.body.weight.BodyWeightEntity
import com.github.mikephil.charting.components.AxisBase
import com.github.mikephil.charting.formatter.ValueFormatter
import java.text.SimpleDateFormat

class FatValueFormatter()
    : ValueFormatter() {
    override fun getAxisLabel(value: Float, axis: AxisBase?): String {
        return "${value} KG"
    }
}