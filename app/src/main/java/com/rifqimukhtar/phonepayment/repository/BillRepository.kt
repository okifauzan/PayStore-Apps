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
    fun getPaymentDetail(sendPhone: SendPhone) : MutableLiveData<PhoneBill>{
        var data = MutableLiveData<PhoneBill>()
        val apiCall = ApiClient.getClient()?.create(ApiInteface::class.java)
        apiCall?.getPaymentDetail(sendPhone)
            ?.enqueue(object : Callback<BaseResponse<PhoneBill>> {
                override fun onResponse(call: Call<BaseResponse<PhoneBill>>,
                    response: Response<BaseResponse<PhoneBill>>
                ) {
                    if (response.isSuccessful) {
                        val item = response.body()?.data
                        val bill = PhoneBill(item?.idBill, item?.telephoneOwner, item?.telephoneNumber, item?.month,
                            item?.amount, item?.status)
                        data.value = bill
                        Log.d("State", item.toString())
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
                    if (response.isSuccessful) {
                        Log.d("State", response.toString())
                        data = MutableLiveData(response.toString())
                        Toast.makeText(context, response.toString(), Toast.LENGTH_SHORT).show()
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