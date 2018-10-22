package laviola.pucminas.espressoapp.tests

import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.action.ViewActions.click
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.contrib.RecyclerViewActions
import android.support.test.espresso.intent.Intents
import android.support.test.espresso.intent.Intents.intended
import android.support.test.espresso.intent.matcher.IntentMatchers.hasComponent
import android.support.test.espresso.matcher.RootMatchers.withDecorView
import android.support.test.espresso.matcher.ViewMatchers.*
import android.support.test.rule.ActivityTestRule
import android.support.v7.widget.RecyclerView
import android.view.View
import com.xwray.groupie.ViewHolder
import laviola.pucminas.espressoapp.R
import laviola.pucminas.espressoapp.ui.CreateUserActivity
import laviola.pucminas.espressoapp.ui.MainActivity
import io.appflate.restmock.RESTMockServer
import io.appflate.restmock.RequestsVerifier
import io.appflate.restmock.utils.RequestMatchers.pathContains
import junit.framework.Assert.assertEquals
import laviola.pucminas.espressoapp.ui.DetailActivity
import org.hamcrest.Matchers.not
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class MainActivityTest : BaseTest<MainActivity>() {

    @Before
    fun setUp() {
        unlockScreen(mTestRule)
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

    @Test
    fun testLoadUsersEmpty() {
        RESTMockServer.whenGET(pathContains("user"))
                .thenReturnFile(200, "users/empty_list.json")
        mTestRule.launchActivity(null)
        RequestsVerifier.verifyGET(pathContains("user")).invoked()
        val list: RecyclerView = mTestRule.activity.findViewById(R.id.userRecycleView)
        assertEquals(View.GONE, list.visibility)
        val placeholder: View = mTestRule.activity.findViewById(R.id.placeHolder)
        assertEquals(View.VISIBLE, placeholder.visibility)
        assertEquals(0, list.adapter?.itemCount)
    }

    @Test
    fun testLoadUserUnauthorized() {
        RESTMockServer.whenGET(pathContains("user"))
                .thenReturnEmpty(401)
        mTestRule.launchActivity(null)
        RequestsVerifier.verifyGET(pathContains("user")).invoked()
        onView(withText(R.string.not_auth)).check(matches(isDisplayed()))
        val list: RecyclerView = mTestRule.activity.findViewById(R.id.userRecycleView)
        assertEquals(View.GONE, list.visibility)
        val placeholder: View = mTestRule.activity.findViewById(R.id.placeHolder)
        assertEquals(View.VISIBLE, placeholder.visibility)
        assertEquals(0, list.adapter?.itemCount)
    }

    @Test
    fun testLoadUserAPIError() {
        RESTMockServer.whenGET(pathContains("user"))
                .thenReturnEmpty(500)
        mTestRule.launchActivity(null)
        RequestsVerifier.verifyGET(pathContains("user")).invoked()
        onView(withText(R.string.error))
                .inRoot(withDecorView(not(mTestRule.activity.window.decorView)))
                .check(matches(isDisplayed()))
        val list: RecyclerView = mTestRule.activity.findViewById(R.id.userRecycleView)
        assertEquals(View.GONE, list.visibility)
        val placeholder: View = mTestRule.activity.findViewById(R.id.placeHolder)
        assertEquals(View.VISIBLE, placeholder.visibility)
        assertEquals(0, list.adapter?.itemCount)
    }

    @Test
    fun testUserItemClick() {
        RESTMockServer.whenGET(pathContains("user"))
                .thenReturnFile(200, "users/user_list.json")
        mTestRule.launchActivity(null)
        RequestsVerifier.verifyGET(pathContains("user")).invoked()
        onView(withId(R.id.userRecycleView))
                .perform(RecyclerViewActions
                        .actionOnItemAtPosition<ViewHolder>(0, click()))
        intended(hasComponent(DetailActivity::class.java.canonicalName))
    }
}