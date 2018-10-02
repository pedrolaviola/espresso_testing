package com.laviola.espressotesting.tests

import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.action.ViewActions.click
import android.support.test.espresso.intent.Intents
import android.support.test.espresso.intent.Intents.intended
import android.support.test.espresso.intent.matcher.IntentMatchers.hasComponent
import android.support.test.espresso.matcher.ViewMatchers.withId
import android.support.test.rule.ActivityTestRule
import android.support.v7.widget.RecyclerView
import android.view.View
import com.laviola.espressotesting.R
import com.laviola.espressotesting.ui.CreateUserActivity
import com.laviola.espressotesting.ui.MainActivity
import io.appflate.restmock.RESTMockServer
import io.appflate.restmock.RequestsVerifier
import io.appflate.restmock.utils.RequestMatchers.pathContains
import junit.framework.Assert.assertEquals
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class MainActivityTest {

    @Before
    fun setUp() {
        Intents.init()
        RESTMockServer.reset()
    }

    @After
    fun tearDown() {
        Intents.release()
    }

    @get:Rule
    val mTestRule = ActivityTestRule<MainActivity>(MainActivity::class.java,
            true, false)

    @Test
    fun testLoadUsersSuccess() {
        RESTMockServer.whenGET(pathContains("user"))
                .thenReturnFile(200, "users/user_list.json")
        mTestRule.launchActivity(null)
        RequestsVerifier.verifyGET(pathContains("user")).invoked()
        val list: RecyclerView = mTestRule.activity.findViewById(R.id.userRecycleView)
        assertEquals(View.VISIBLE, list.visibility)
        assertEquals(3, list.adapter?.itemCount)
    }

    @Test
    fun testCreateUserClick() {
        RESTMockServer.whenGET(pathContains("user"))
                .thenReturnFile(200, "users/user_list.json")
        mTestRule.launchActivity(null)
        onView(withId(R.id.addBtn)).perform(click())
        intended(hasComponent(CreateUserActivity::class.java.canonicalName))
    }
}