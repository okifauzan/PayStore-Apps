package com.rifqimukhtar.phonepayment.rest

import com.rifqimukhtar.phonepayment.db.entity.*
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface ApiInteface {
    @POST("login")
    fun postLogin(@Body login: Login): Call<LoginResponse>

    @POST("otp")
    fun postOTP(@Body otp: SendOTP): Call<SendOTPResponse>

    @POST("user")
    fun postRegister(@Body createAccount: CreateAccount): Call<CreateAccountResponse>

    @GET("telephone_bill")
    fun getTelephoneBill(@Body phone: SendPhone): Call<PhoneBill>

    @GET("user")
    fun getUser(userId: Int): Call<User>

//    @POST
//    fun payTelephoneBill(@Body phoneBill: SendPhoneBill): Call<>
}