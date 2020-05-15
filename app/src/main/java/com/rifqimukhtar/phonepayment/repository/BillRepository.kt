package com.rifqimukhtar.phonepayment.repository

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import com.rifqimukhtar.phonepayment.db.entity.*
import com.rifqimukhtar.phonepayment.rest.ApiClient
import com.rifqimukhtar.phonepayment.rest.ApiInteface
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class BillRepository(val context: Context) {
    fun getPaymentDetail(sendUser: SendUser) : MutableLiveData<PhoneBill>{
        var data = MutableLiveData<PhoneBill>()
        val apiCall = ApiClient.getClient()?.create(ApiInteface::class.java)
        apiCall?.getPaymentDetail(sendUser)
            ?.enqueue(object : Callback<ArrayList<PhoneBill>> {
                override fun onResponse(call: Call<ArrayList<PhoneBill>>, response: Response<ArrayList<PhoneBill>>) {
                    if (response.isSuccessful) {
                        val item = response.body()!!.get(0)
                        val bill = PhoneBill(item.idPayment, item.idUser, item.name, item.phoneNumber,
                            item.balance, item.idBill, item.telephoneNumber, item.amount, item.idPaymentMethod,
                            item.method, item.timestamp)
                        data.value = bill
                        Log.d("State", item.toString())
                    }
                }
                override fun onFailure(call: Call<ArrayList<PhoneBill>>, t: Throwable) {
                    Toast.makeText(context, "Request Failed $t", Toast.LENGTH_SHORT).show()
                    Log.d("Failed", t.message)
                }
            })
        return data
    }

    fun sendPaymentRequest(sendRequestPayment: SendRequestPayment) : MutableLiveData<BaseResponse<Any>>{
        var data = MutableLiveData<BaseResponse<Any>>()
        val apiCall = ApiClient.getClient()?.create(ApiInteface::class.java)
        apiCall?.sendRequestPayment(sendRequestPayment)
            ?.enqueue(object : Callback<BaseResponse<Any>> {
                override fun onResponse(call: Call<BaseResponse<Any>>, response: Response<BaseResponse<Any>>) {
                    if (response.isSuccessful) {
                        Log.d("State", response.toString())
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