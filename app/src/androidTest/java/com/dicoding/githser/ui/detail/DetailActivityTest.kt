package com.dicoding.githser.ui.detail

import android.content.Intent
import androidx.test.core.app.ActivityScenario
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import com.dicoding.githser.R
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4ClassRunner::class)
class DetailActivityTest {
    private val keyUsername = "luqmanulhakiem"
    private val dummyUsername = "luqmanulhakiem"

    @Before
    fun init() {
        val intent =
            Intent(ApplicationProvider.getApplicationContext(), DetailActivity::class.java).apply {
                putExtra(DetailActivity.KEY_USERNAME, keyUsername)
            }
        ActivityScenario.launch<DetailActivity>(intent)
        Thread.sleep(10000)
    }

    @Test
    fun addFavorites() {
        onView(withId(R.id.tiUsername)).check(matches(isDisplayed()))
        onView(withId(R.id.tiUsername)).check(matches(withText(dummyUsername)))
        onView(withId(R.id.fab_fav)).check(matches(isDisplayed()))
        onView(withId(R.id.fab_fav)).perform(click())
    }
}