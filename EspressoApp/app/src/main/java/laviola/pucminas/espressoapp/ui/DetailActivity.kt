package laviola.pucminas.espressoapp.ui

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_detail.*
import laviola.pucminas.espressoapp.R
import laviola.pucminas.espressoapp.model.User

class DetailActivity : AppCompatActivity() {
    companion object {
        const val parcelableKey = "user_key"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        val user = intent.getParcelableExtra<User>(parcelableKey)
        user.let {
            userNameText.text = it.name
            userJobText.text = it.job
        }
    }
}
