package com.laviola.espressotesting.ui

import android.app.Dialog
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import com.laviola.espressotesting.R
import com.laviola.espressotesting.base.BaseActivity
import com.laviola.espressotesting.model.User
import com.laviola.espressotesting.service.RetrofitInterface
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_create_user.*
import org.jetbrains.anko.alert

class CreateUserActivity : BaseActivity() {
    private val retrofitInterface by lazy {
        RetrofitInterface.create()
    }
    private var disposable: Disposable? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_user)

        addBtn.setOnClickListener {
            if (isFieldsChecked())
                createUser(edtUserName.text.toString(), edtUserJob.text.toString())
            else
                displayDialog()
        }
    }

    private fun displayDialog() {
        alert(getString(R.string.empty_fields)) {
            title = getString(R.string.alert_title)
            positiveButton("Ok") { dialog -> dialog.dismiss() }
        }.show()
    }

    private fun createUser(name: String, job: String) {
        disposable = retrofitInterface
                .createUser(User(name, job))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        { onBackPressed() },
                        { error -> showError(error) }
                )
    }

    private fun isFieldsChecked(): Boolean {
        return !TextUtils.isEmpty(edtUserJob.text) && !TextUtils.isEmpty(edtUserName.text)
    }
}
