package com.rifqimukhtar.phonepayment.rest

import com.rifqimukhtar.phonepayment.db.entity.Login
import com.rifqimukhtar.phonepayment.db.entity.LoginResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface ApiInteface {
    @POST("login")
    fun postLogin(@Body login: Login): Call<LoginResponse>
}