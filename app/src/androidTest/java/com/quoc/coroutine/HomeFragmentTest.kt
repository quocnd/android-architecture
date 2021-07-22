package com.quoc.coroutine

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.quoc.coroutine.test.launchFragmentInHiltContainer
import com.quoc.coroutine.ui.home.HomeFragment
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@HiltAndroidTest
@RunWith(AndroidJUnit4::class)
class HomeFragmentTest {

    @get:Rule(order = 0)
    var hiltRule = HiltAndroidRule(this)

    @Before
    fun setUp() {
        launchFragmentInHiltContainer<HomeFragment>()
    }

    @Test
    fun testRefresh() {
        onView(withId(R.id.swipeLayout)).perform(ViewActions.swipeDown())
//        onView(withId(R.id.tvAuthor)).check(matches(withText("Paul Jarvis")))
        onView(withId(R.id.recyclerView)).check(matches(isDisplayed()))
    }

}