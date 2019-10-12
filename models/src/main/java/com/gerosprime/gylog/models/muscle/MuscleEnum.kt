package com.gerosprime.gylog.models.muscle

import androidx.annotation.IdRes
import com.gerosprime.gylog.models.R

enum class MuscleEnum(@IdRes val stringResId: Int) {
    TRICEPS(R.string.triceps),
    BICEPS(R.string.biceps),
    CHEST(R.string.chest),
    CHEST_LOWER(R.string.chest_lower),
    CHEST_UPPER(R.string.chest_upper),
    BACK_UPPER(R.string.back_upper),
    BACK_LOWER(R.string.back_lower),
    HAMSTRINGS(R.string.hamstrings),
    FOREARMS(R.string.forearms),
    SHOULDER_SIDE(R.string.shoulder_side),
    SHOULDER_FRONT(R.string.shoulder_front),
    SHOULDER_BACK(R.string.shoulder_back),
    TRAPS(R.string.traps),
    ABS(R.string.abs),
    QUADS(R.string.quads),
    CALVES(R.string.calves),
}