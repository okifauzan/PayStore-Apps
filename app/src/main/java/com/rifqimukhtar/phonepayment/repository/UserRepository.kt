package com.rifqimukhtar.phonepayment.repository

import android.content.Context
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
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
    val API_KEY = "XXXX"
    fun getUser(sendUser: SendUser) : MutableLiveData<User>{
        var data = MutableLiveData<User>()
        val apiCall = ApiClient.getClient()?.create(ApiInteface::class.java)
        apiCall?.getUser(sendUser)
            ?.enqueue(object : Callback<BaseUser> {
                override fun onResponse(call: Call<BaseUser>, response: Response<BaseUser>) {
                    if (response.isSuccessful) {
                        val item = response.body()!!.userProfile
                        val user = User(item?.idUser, item?.name, item?.email, item?.password, item?.phoneNumber, item?.balance, item?.token)
                        data.value = user
                        Log.d("State", item.toString())
                    }
                }
                override fun onFailure(call: Call<BaseUser>, t: Throwable) {
                    Toast.makeText(context, "Request Failed", Toast.LENGTH_SHORT).show()
                    Log.d("Failed", t.message)
                }
            })
        return data
    }
}