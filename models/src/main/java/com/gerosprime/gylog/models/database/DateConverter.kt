package com.gerosprime.gylog.models.database

import androidx.room.TypeConverter
import java.util.*

class DateConverter {

    @TypeConverter
    fun dateToLong(date: Date?) : Long? {

        if (date == null) return null

        return date.time
    }

    @TypeConverter
    fun longToDate(timeMillis : Long?) : Date? {

        if (timeMillis == null) return null

        return Date(timeMillis)
    }
}