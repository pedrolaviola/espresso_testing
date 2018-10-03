package laviola.pucminas.espressoapp.tests

import android.content.Intent
import android.support.test.InstrumentationRegistry
import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.intent.Intents
import android.support.test.espresso.matcher.ViewMatchers.*
import android.support.test.rule.ActivityTestRule
import laviola.pucminas.espressoapp.model.User
import laviola.pucminas.espressoapp.ui.DetailActivity
import laviola.pucminas.espressoapp.R
import org.hamcrest.CoreMatchers.containsString
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class DetailActivityTest {

    @get:Rule
    val mTestRule = ActivityTestRule<DetailActivity>(
            DetailActivity::class.java, true, false)

    @Before
    fun setUp() {
        Intents.init()
    }

    @After
    fun tearDown() {
        Intents.release()
    }

    @Test
    fun testUserDisplayed() {
        val intent = Intent(
                InstrumentationRegistry.getInstrumentation().targetContext,
                DetailActivity::class.java)
        intent.putExtra(DetailActivity.parcelableKey, User("Pedro", "Dev"))
        mTestRule.launchActivity(intent)
        onView(withId(R.id.userNameText))
                .check(matches(withText(containsString("Pedro"))))
                .check(matches(isDisplayed()))
        onView(withId(R.id.userJobText))
                .check(matches(withText(containsString("Dev"))))
                .check(matches(isDisplayed()))
    }
}