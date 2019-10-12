package com.gerosprime.gylog.base.utils

import android.view.View
import com.gerosprime.gylog.base.FetchState

object FetchStateUtils {
    fun updateViewContentsByState(fetchState : FetchState, mainContent : View,
                                  progressContent : View, errorContent : View) {
        when (fetchState) {

            FetchState.FETCHING -> {
                mainContent.visibility = View.INVISIBLE
                progressContent.visibility = View.VISIBLE
                errorContent.visibility = View.INVISIBLE
            }
            FetchState.LOADED -> {
                mainContent.visibility = View.VISIBLE
                progressContent.visibility = View.INVISIBLE
                errorContent.visibility = View.INVISIBLE
            }
            FetchState.ERROR -> {
                mainContent.visibility = View.INVISIBLE
                progressContent.visibility = View.INVISIBLE
                errorContent.visibility = View.VISIBLE
            }

        }
    }
}