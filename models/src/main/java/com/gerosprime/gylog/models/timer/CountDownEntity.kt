package com.gerosprime.gylog.models.timer

import java.util.concurrent.atomic.AtomicInteger

class CountDownEntity (seconds : Int) {

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

}