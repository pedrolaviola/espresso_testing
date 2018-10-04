package laviola.pucminas.espressoapp.base

import android.annotation.SuppressLint
import android.support.v7.app.AppCompatActivity
import laviola.pucminas.espressoapp.R
import org.jetbrains.anko.alert
import org.jetbrains.anko.toast

@SuppressLint("Registered")
open class BaseActivity : AppCompatActivity() {
    protected fun showError() {
        toast(getString(R.string.error))
    }

    protected fun showAlert(messageId: Int) {
        alert(getString(messageId)) {
            title = getString(R.string.alert_title)
            positiveButton("Ok") { dialog -> dialog.dismiss() }
        }.show()
    }
}