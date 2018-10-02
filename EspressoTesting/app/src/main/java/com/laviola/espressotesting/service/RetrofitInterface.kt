package com.laviola.espressotesting.service

import com.laviola.espressotesting.app.MyApplication
import com.laviola.espressotesting.model.User
import io.reactivex.Observable
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface RetrofitInterface {

    @GET("user")
    fun getUsers(): Observable<Array<User>>

    @POST("user")
    fun createUser(@Body user: User): Observable<User>

    companion object {
        fun create(): RetrofitInterface {

            val retrofit = Retrofit.Builder()
                    .addCallAdapterFactory(
                            RxJava2CallAdapterFactory.create())
                    .addConverterFactory(
                            GsonConverterFactory.create())
                    .baseUrl(MyApplication.instance.getApiUrl())
                    .build()

            return retrofit.create(RetrofitInterface::class.java)
        }
    }
}