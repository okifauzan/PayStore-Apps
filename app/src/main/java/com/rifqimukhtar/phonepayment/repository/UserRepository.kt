package com.rifqimukhtar.phonepayment.repository

import android.content.Context
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import com.rifqimukhtar.phonepayment.db.entity.BaseResponse
import com.rifqimukhtar.phonepayment.db.entity.BaseUser
import com.rifqimukhtar.phonepayment.db.entity.SendUser
import com.rifqimukhtar.phonepayment.db.entity.User
import com.rifqimukhtar.phonepayment.rest.ApiClient
import com.rifqimukhtar.phonepayment.rest.ApiInteface
import kotlinx.android.synthetic.main.activity_main_menu.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UserRepository (val context: Context){

    fun getUser(sendUser: SendUser) : MutableLiveData<User>{
        var data = MutableLiveData<User>()
        val apiCall = ApiClient.getClient()?.create(ApiInteface::class.java)
        apiCall?.getUser(sendUser)
            ?.enqueue(object : Callback<BaseResponse<User>> {
                override fun onResponse(call: Call<BaseResponse<User>>, response: Response<BaseResponse<User>>) {
                    if (response.isSuccessful) {
                        val item = response.body()!!.data
                        val user = User(item?.idUser, item?.name, item?.email, item?.password, item?.phoneNumber, item?.balance, item?.token)
                        data.value = user
                        Log.d("State", item.toString())
                    }
                }
                override fun onFailure(call: Call<BaseResponse<User>>, t: Throwable) {
                    Toast.makeText(context, "Request Failed", Toast.LENGTH_SHORT).show()
                    Log.d("Failed", t.message)
                }
            })
        return data
    }
}