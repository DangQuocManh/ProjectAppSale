package com.quocmanh.projectappsale.retrofit

import com.quocmanh.projectappsale.myinterface.LoginCallApi
import hu.akarnokd.rxjava3.retrofit.RxJava3CallAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitFactor {
    fun createRetrofit() : LoginCallApi{
      return Retrofit.Builder().baseUrl("http://192.168.0.4:8080")
          .addConverterFactory(GsonConverterFactory.create())
          .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
          .build().create(LoginCallApi::class.java)
    }
}