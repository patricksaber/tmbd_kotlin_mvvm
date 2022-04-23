package com.neugelb.ui.activity

import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.intent.Intents
import androidx.test.espresso.intent.Intents.intended
import androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.platform.app.InstrumentationRegistry
import com.neugelb.R
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test


class LoginActivityTest {


    @Rule
    @JvmField
    var activityRule = ActivityScenarioRule(LoginActivity::class.java)


    @Test
    fun useAppContext() {
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        assertEquals("com.skeleton", appContext.packageName)
    }

    @Test
    fun userCanEnterNameOrEmail() {
        Espresso.onView(withId(R.id.username)).perform(click()).perform(typeText("Patrick"))
    }

    @Test
    fun userCanEnterPassword() {
        Espresso.onView(withId(R.id.password)).perform(click()).perform(typeText("123456"))
    }

    @Before
    fun setUp() {
        Intents.init()
    }

    @After
    fun tearDown() {
        Intents.release()
    }

    @Test
    fun checkUserNameAndPassword() {
        val username = withId(R.id.username)
        val password = withId(R.id.password)
        val login = withId(R.id.login)
        Espresso.onView(username).perform(typeText("admin"))
        Espresso.onView(password).perform(typeText("admin"))
        activityRule.scenario.onActivity { activity ->
            activity.binding.login.isEnabled = true
        }
        Espresso.onView(login).perform(click())
        runBlocking {
            delay(3000)
            intended(hasComponent(MainActivity::class.java.name))
        }
    }


}