package com.neugelb.ui

import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.intent.Intents
import androidx.test.espresso.intent.matcher.IntentMatchers
import androidx.test.ext.junit.rules.ActivityScenarioRule
import com.neugelb.R
import com.neugelb.ui.activity.DetailActivity
import com.neugelb.ui.activity.MainActivity
import com.neugelb.utils.TestUtils
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test


class MainActivityTest {

    @Rule
    @JvmField
    var activityRule = ActivityScenarioRule(MainActivity::class.java)


    @Before
    fun setUp() {
        Intents.init()
    }

    @After
    fun tearDown() {
        Intents.release()
    }


    @Test
    fun listTest() {
        runBlocking {
            launch {
                delay(3000)
                Espresso.onView(TestUtils.NthMatcher(R.id.recyclerview, 2))
                    .perform(
                        RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(
                            2,
                            click()
                        )
                    )

                delay(1000)
                Intents.intended(IntentMatchers.hasComponent(DetailActivity::class.java.name))
            }
        }
    }

}