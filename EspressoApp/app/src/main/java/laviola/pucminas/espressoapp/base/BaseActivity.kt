package laviola.pucminas.espressoapp.base

import android.annotation.SuppressLint
import android.support.v7.app.AppCompatActivity
import org.jetbrains.anko.toast

@SuppressLint("Registered")
open class BaseActivity : AppCompatActivity() {
    protected fun showError(message: Throwable) {
        toast("error")
        //Tfasdfsadfjnjkjkjahfsa    q   sa
    }
}