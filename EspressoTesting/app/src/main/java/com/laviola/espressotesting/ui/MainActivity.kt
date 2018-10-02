package com.laviola.espressotesting.ui

import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.laviola.espressotesting.R
import com.laviola.espressotesting.base.BaseActivity
import com.laviola.espressotesting.model.User
import com.laviola.espressotesting.service.RetrofitInterface
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.ViewHolder
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*
import com.microsoft.appcenter.crashes.Crashes
import com.microsoft.appcenter.analytics.Analytics
import com.microsoft.appcenter.AppCenter




class MainActivity : BaseActivity() {
    private val retrofitInterface by lazy {
        RetrofitInterface.create()
    }
    private var disposable: Disposable? = null

    private val adapter = GroupAdapter<ViewHolder>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        AppCenter.start(application, "ba48b1cf-66c2-4e0d-acc8-6e41b56181c3",
                Analytics::class.java, Crashes::class.java)
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
                        { result -> showUsers(result) },
                        { error ->
                            showError(error)
                            showPlaceHolder()
                        }
                )
    }

    private fun showUsers(result: Array<User>) {
        if (result.isEmpty())
            showPlaceHolder()
        else {
            hidePlaceholder()
            for (user in result) {
                adapter.add(UserItem(user))
            }
        }
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

