package com.example.fieldwork.webservice

import android.util.Log
import com.example.fieldwork.BuildConfig
import com.example.fieldwork.application.MyApp
import com.example.fieldwork.utils.MySharedPref
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import java.util.concurrent.TimeUnit

object ApiClient {
    private const val BASE_URL= BuildConfig.API_URL;
    private const val READ_TIMEOUT =BuildConfig.READ_TIMEOUT;
    private const val CONNECT_TIMEOUT =BuildConfig.CONNECT_TIMEOUT;
    private val logInterceptor by lazy {
       HttpLoggingInterceptor().apply {
           level=if(BuildConfig.DEBUG)
           {
               HttpLoggingInterceptor.Level.BODY
           }else{
               HttpLoggingInterceptor.Level.NONE
           }
       }
   }
    private val getOkHttpClient by lazy {
        OkHttpClient.Builder()
            .addInterceptor{
                chain->
                val request=chain.request().newBuilder()
                    .addHeader("Authorization", getAuthToken())
                    .build()
                chain.proceed(request)
            }.addInterceptor(logInterceptor)
            .addInterceptor(AuthInterceptor(MyApp().getMyApplicationContext()))
            .connectTimeout(CONNECT_TIMEOUT.toLong(),TimeUnit.SECONDS)
            .readTimeout(READ_TIMEOUT.toLong(),TimeUnit.SECONDS)
            .build()
    }
    private val retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(getOkHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
    fun  create():ApiService{
        return retrofit.create(ApiService::class.java)
    }
    private fun getAuthToken():String{

        val pref=MySharedPref(MyApp().getMyApplicationContext())
        Log.d("mytag", pref.getToken())
        return pref.getToken()
    }
}