package laviola.pucminas.espressoapp.ui

import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.ViewHolder
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*
import laviola.pucminas.espressoapp.R
import laviola.pucminas.espressoapp.base.BaseActivity
import laviola.pucminas.espressoapp.listener.UserListener
import laviola.pucminas.espressoapp.model.User
import laviola.pucminas.espressoapp.service.RetrofitInterface
import retrofit2.HttpException


class MainActivity : BaseActivity() {
    private val retrofitInterface by lazy {
        RetrofitInterface.create()
    }
    private var disposable: Disposable? = null
    private val adapter = GroupAdapter<ViewHolder>()
    private lateinit var userList: Array<User>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        userRecycleView.layoutManager = LinearLayoutManager(this)
        userRecycleView.adapter = adapter
        addBtn.setOnClickListener {
            startActivity(Intent(this, CreateUserActivity::class.java))
        }
        loadUsers()
    }

    private fun loadUsers() {
        disposable = retrofitInterface
                .getUsers()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        { result ->
                            userList = result
                            showUsers(result)
                        },
                        { error ->
                            var httpException : HttpException? = null
                            try {
                                httpException = error as HttpException
                            } catch (ignored : Exception){

                            }
                            if (httpException?.code() == 401)
                                showAlert(R.string.not_auth)
                            else
                                showError()
                            showPlaceHolder()
                        }
                )
    }

    private fun showUsers(result: Array<User>) {

        if (result.isEmpty())
            showPlaceHolder()
        else {
            val listener = object : UserListener {
                override fun onUserClick(user: User) {
                    startUserDetail(user)
                }
            }
            hidePlaceholder()
            for (user in result) {
                adapter.add(UserItem(user, listener))
            }
        }
    }

    private fun startUserDetail(user: User) {
        val intent = Intent(this, DetailActivity::class.java)
        intent.putExtra(DetailActivity.parcelableKey, user)
        startActivity(intent)
    }

    private fun showPlaceHolder() {
        userRecycleView.visibility = View.GONE
        placeHolder.visibility = View.VISIBLE
    }

    private fun hidePlaceholder() {
        userRecycleView.visibility = View.VISIBLE
        placeHolder.visibility = View.GONE
    }
}

