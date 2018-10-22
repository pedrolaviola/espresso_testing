package laviola.pucminas.espressoapp.tests

import android.support.test.rule.ActivityTestRule
import android.support.v7.app.AppCompatActivity
import android.view.WindowManager


open class BaseTest<T : AppCompatActivity> {
    fun unlockScreen(mActivityRule: ActivityTestRule<T>) {
        val activity = mActivityRule.activity
        val wakeUpDevice = Runnable {
            activity.window.addFlags(WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON or
                    WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED or
                    WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
        }
        activity.runOnUiThread(wakeUpDevice)
    }
}