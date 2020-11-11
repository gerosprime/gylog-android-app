package com.gerosprime.gylog.models

open class Result(private val resultInt : Int) {

    object ResultConstant {
        const val RESULT_SUCCESS = 0
        const val RESULT_ERROR = 1
    }
}