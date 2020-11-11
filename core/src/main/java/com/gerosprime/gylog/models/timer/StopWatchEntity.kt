package com.gerosprime.gylog.models.timer

import java.util.*
import java.util.concurrent.atomic.AtomicInteger

class StopWatchEntity(seconds : Int = 0) {
    private var atomicInteger = AtomicInteger(seconds)

    val seconds : Int
        get() = atomicInteger.get()

    fun addSeconds(seconds : Int) {
        if (atomicInteger.get() + seconds > 0) {
            atomicInteger.addAndGet(seconds)
        } else {
            atomicInteger.set(0)
        }

    }

    fun toTimeString() : String {

        val hour = (seconds / 60) / 60
        val mins = (seconds / 60) % 60
        val seconds = seconds % 60

        return String.format(Locale.getDefault(), "%02d:%02d:%02d", hour, mins, seconds)
    }

}