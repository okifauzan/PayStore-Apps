package com.rifqimukhtar.phonepayment.rest

import android.content.Context
import com.rifqimukhtar.phonepayment.R
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiClient {
    var retrofit: Retrofit? = null
    const val apiKey = "XXXX"
    const val baseURL = "https://infinite-waters-35921.herokuapp.com/api/paystore/"


    fun getClient(): Retrofit? {
        if (retrofit == null) {
            val client = OkHttpClient().newBuilder().addInterceptor { chain ->
                val request = chain.request()
                    .newBuilder().addHeader("Authorization", apiKey)
                    .build()
                chain.proceed(request)
            }.build()
            retrofit = Retrofit.Builder()
                .client(client).baseUrl(baseURL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }
        return retrofit
    }
}