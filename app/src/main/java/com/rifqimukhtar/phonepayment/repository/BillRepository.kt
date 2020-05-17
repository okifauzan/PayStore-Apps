package com.rifqimukhtar.phonepayment.repository

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import com.google.gson.GsonBuilder
import com.rifqimukhtar.phonepayment.db.entity.*
import com.rifqimukhtar.phonepayment.rest.ApiClient
import com.rifqimukhtar.phonepayment.rest.ApiInteface
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.IOException


class BillRepository(val context: Context) {
    fun getPaymentDetail(sendPhone: SendPhone) : MutableLiveData<PhoneBill>{
        var data = MutableLiveData<PhoneBill>()
        val apiCall = ApiClient.getClient()?.create(ApiInteface::class.java)
        apiCall?.getPaymentDetail(sendPhone)
            ?.enqueue(object : Callback<BaseResponse<PhoneBill>> {
                override fun onResponse(call: Call<BaseResponse<PhoneBill>>,
                    response: Response<BaseResponse<PhoneBill>>
                ) {
                    if (response.code() == 404)
                    {
                        //data.value = response.body()!!.data
                        val gson = GsonBuilder().create()
                        var failResponse = PhoneBill(0,"","","",1,"")
                        try {
                            failResponse = gson.fromJson(response.errorBody()!!.string(), PhoneBill::class.java)

                            data.value = failResponse
                        } catch (e: IOException) {
                        }
                    } else if (response.isSuccessful){
                        data.value = response.body()!!.data
                        Log.d("State", "Success get payment ${response.body()!!.status}")
                    }

                }
                override fun onFailure(call: Call<BaseResponse<PhoneBill>>, t: Throwable) {
                    Toast.makeText(context, "Request Failed $t", Toast.LENGTH_SHORT).show()
                    Log.d("Failed", t.message)
                }
            })
        return data
    }

    fun sendPaymentRequest(sendRequestPayment: SendRequestPayment) : MutableLiveData<String>{
        var data = MutableLiveData<String>()
        val apiCall = ApiClient.getClient()?.create(ApiInteface::class.java)
        apiCall?.sendRequestPayment(sendRequestPayment)
            ?.enqueue(object : Callback<BaseResponse<Any>> {
                override fun onResponse(call: Call<BaseResponse<Any>>, response: Response<BaseResponse<Any>>) {
                    if (response.code() == 404) {
                        //data.value = response.body()!!.data
                        val gson = GsonBuilder().create()
                        var failResponse = "Not Found"
                        try {
                            failResponse = gson.fromJson(
                                response.errorBody()!!.string(),
                                String::class.java
                            )

                            data.value = failResponse
                        } catch (e: IOException) {
                        }
                    }else if (response.isSuccessful){
                        Log.d("State", response.body()!!.message.toString())
                        val message = response.body()!!.message.toString()
                        data = MutableLiveData(message)
                    }
                }
                override fun onFailure(call: Call<BaseResponse<Any>>, t: Throwable) {
                    Toast.makeText(context, "Request Failed $t", Toast.LENGTH_SHORT).show()
                    Log.d("Failed", t.message)
                }
            })
        return data
    }
}