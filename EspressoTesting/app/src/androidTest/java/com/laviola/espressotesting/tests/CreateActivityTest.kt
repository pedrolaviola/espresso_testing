package com.laviola.espressotesting.tests

import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.action.ViewActions.*
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.intent.Intents
import android.support.test.espresso.matcher.RootMatchers.isDialog
import android.support.test.espresso.matcher.ViewMatchers.*
import android.support.test.rule.ActivityTestRule
import com.laviola.espressotesting.R
import com.laviola.espressotesting.ui.CreateUserActivity
import io.appflate.restmock.RESTMockServer
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class CreateActivityTest {

    @Before
    fun setUp() {
        RESTMockServer.reset()
        Intents.init()
    }

    @After
    fun tearDown() {
        Intents.release()
    }

    @get:Rule
    val mTestRule = ActivityTestRule<CreateUserActivity>(CreateUserActivity::class.java,
            true, false)

    @Test
    fun testEmptyFieldsDialog() {
        mTestRule.launchActivity(null)
        onView(withId(R.id.addBtn))
                .perform(click())
        onView(withText(R.string.empty_fields))
                .inRoot(isDialog())
                .check(matches(isDisplayed()))
                .perform(pressBack())

        onView(withId(R.id.edtUserName))
                .perform(typeText("Pedro"), closeSoftKeyboard())
        onView(withId(R.id.addBtn))
                .perform(click())
        onView(withText(R.string.empty_fields))
                .inRoot(isDialog())
                .check(matches(isDisplayed()))
                .perform(pressBack())

        onView(withId(R.id.edtUserName))
                .perform(clearText(), closeSoftKeyboard())
        onView(withId(R.id.edtUserJob))
                .perform(typeText("Dev"), closeSoftKeyboard())
        onView(withId(R.id.addBtn))
                .perform(click())
        onView(withText(R.string.empty_fields))
                .inRoot(isDialog())
                .check(matches(isDisplayed()))
                .perform(pressBack())

    }
}