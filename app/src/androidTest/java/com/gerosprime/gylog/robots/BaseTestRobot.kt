package com.gerosprime.gylog.robots

import android.view.View
import androidx.annotation.IdRes
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso
import androidx.test.espresso.Espresso.closeSoftKeyboard
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.UiController
import androidx.test.espresso.ViewAction
import androidx.test.espresso.ViewInteraction
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.withId
import org.hamcrest.Matcher

open class BaseTestRobot {

    fun clearInputText(@IdRes resId : Int) : ViewInteraction {
        return onView(withId(resId))
            .perform(ViewActions.clearText())
    }

    fun swipeDown(@IdRes resId : Int) : ViewInteraction {
        return onView(withId(resId))
            .perform(ViewActions.swipeDown())
    }

    fun swipeUp(@IdRes resId : Int) : ViewInteraction {
        return onView(withId(resId))
            .perform(ViewActions.swipeUp())
    }

    fun swipeLeft(@IdRes resId : Int) : ViewInteraction {
        return onView(withId(resId))
            .perform(ViewActions.swipeLeft())
    }

    fun swipeRight(@IdRes resId : Int) : ViewInteraction {
        return onView(withId(resId))
            .perform(ViewActions.swipeRight())
    }

    fun scrollTo(@IdRes resId : Int) : ViewInteraction {
        return onView(withId(resId))
            .perform(ViewActions.scrollTo())

    }

    fun closeInputKeyboard() {
        closeSoftKeyboard()
    }

    fun pressBackButton() {
        Espresso.pressBack()
    }

    fun inputText(@IdRes resId : Int, text : String) : ViewInteraction {
        return onView(withId(resId))
            .perform(ViewActions.typeText(text))
    }

    fun click(@IdRes resId : Int) : ViewInteraction {
        return onView(withId(resId)).perform(ViewActions.click())

    }

    fun doubleClick(@IdRes resId : Int) : ViewInteraction {
        return onView(withId(resId)).perform(ViewActions.doubleClick())
    }

    fun longClick(@IdRes resId : Int) : ViewInteraction {
        return onView(withId(resId)).perform(ViewActions.longClick())
    }

    fun <VH : RecyclerView.ViewHolder>
            recyclerViewItemClick(@IdRes resId : Int, position : Int) : ViewInteraction {
        return onView(withId(resId))
            .perform(RecyclerViewActions.actionOnItemAtPosition<VH>(position, ViewActions.click()))
    }

    fun <VH : RecyclerView.ViewHolder>
            recyclerViewItemScrollTo(@IdRes resId : Int, position : Int) : ViewInteraction {
        return onView(withId(resId))
            .perform(RecyclerViewActions.scrollToPosition<VH>(position))
    }

    class ViewHolderItemClick(@IdRes val id : Int) : ViewAction {
        override fun getDescription(): String {
            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }

        override fun getConstraints(): Matcher<View> {
            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }

        override fun perform(uiController: UiController?, view: View?) {
            val item : View? = view?.findViewById(id)
            item?.performClick()
        }
    }

}