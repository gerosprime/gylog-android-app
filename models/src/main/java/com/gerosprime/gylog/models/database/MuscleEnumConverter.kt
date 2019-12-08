package com.gerosprime.gylog.models.database

import androidx.room.TypeConverter
import com.gerosprime.gylog.models.muscle.MuscleEnum
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class MuscleEnumConverter {

    private val gson = Gson()

    @TypeConverter
    fun muscleToString(muscles : ArrayList<MuscleEnum>?) : String? {

        if (muscles == null) return null

        return gson.toJson(muscles)
    }

    @TypeConverter
    fun stringToMuscle(muscleString : String?) : ArrayList<MuscleEnum>? {

        if (muscleString == null) return null

        return gson.fromJson(muscleString, object : TypeToken<ArrayList<MuscleEnum>>() {}.type)
    }
}