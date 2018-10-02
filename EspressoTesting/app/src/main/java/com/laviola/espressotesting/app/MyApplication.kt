package com.laviola.espressotesting.app

import android.app.Application

open class MyApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        instance = this
    }

    companion object {
        lateinit var instance: MyApplication
            private set
    }

    open fun getApiUrl(): String {
        return "http://teste.com/"
    }
}