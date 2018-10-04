package laviola.pucminas.espressoapp.ui

import android.os.Bundle
import android.text.TextUtils
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_create_user.*
import laviola.pucminas.espressoapp.R
import laviola.pucminas.espressoapp.base.BaseActivity
import laviola.pucminas.espressoapp.model.User
import laviola.pucminas.espressoapp.service.RetrofitInterface

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
                showAlert(R.string.empty_fields)
        }
    }

    private fun createUser(name: String, job: String) {
        disposable = retrofitInterface
                .createUser(User(name, job))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        { onBackPressed() },
                        { showError() }
                )
    }

    private fun isFieldsChecked(): Boolean {
        return !TextUtils.isEmpty(edtUserJob.text) && !TextUtils.isEmpty(edtUserName.text)
    }
}
