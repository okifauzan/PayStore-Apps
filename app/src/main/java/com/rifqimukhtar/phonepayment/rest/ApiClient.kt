package com.rifqimukhtar.phonepayment.rest

import android.content.Context
import com.rifqimukhtar.phonepayment.R
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiClient {
    var retrofit: Retrofit? = null

    fun getClient(apiKey: String, context: Context): Retrofit? {
        if (retrofit == null) {
            val client = OkHttpClient().newBuilder().addInterceptor { chain ->
                val request = chain.request()
                    .newBuilder().addHeader("Authorization", apiKey)
                    .build()
                chain.proceed(request)
            }.build()

            retrofit = Retrofit.Builder()
                .client(client).baseUrl(context.resources.getString(R.string.base_url))
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }
        return retrofit
    }
}