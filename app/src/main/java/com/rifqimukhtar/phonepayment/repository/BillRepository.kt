package com.rifqimukhtar.phonepayment.repository

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.rifqimukhtar.phonepayment.db.entity.*
import com.rifqimukhtar.phonepayment.rest.ApiClient
import com.rifqimukhtar.phonepayment.rest.ApiInteface
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class BillRepository(val context: Context) {
    fun getPaymentDetail(sendPhone: SendPhone) : MutableLiveData<BasePaymentResponse<PhoneBill>>{
        var data = MutableLiveData<BasePaymentResponse<PhoneBill>>()
        val apiCall = ApiClient.getClient()?.create(ApiInteface::class.java)
        apiCall?.getPaymentDetail(sendPhone)
            ?.enqueue(object : Callback<BasePaymentResponse<PhoneBill>> {
                override fun onResponse(call: Call<BasePaymentResponse<PhoneBill>>,
                    response: Response<BasePaymentResponse<PhoneBill>>
                ) {
                        data = MutableLiveData(response.body()!!)
                        Log.d("State", "Success get payment")

                }

                override fun onFailure(call: Call<BasePaymentResponse<PhoneBill>>, t: Throwable) {
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
                    if (response.isSuccessful) {
                        Log.d("State", response.toString())
                        val message = response.body()!!.message.toString()
                        data = MutableLiveData(message)
                        // Toast.makeText(context, response.toString(), Toast.LENGTH_SHORT).show()
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