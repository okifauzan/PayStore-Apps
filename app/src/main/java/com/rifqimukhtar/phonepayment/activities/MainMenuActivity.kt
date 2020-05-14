package com.rifqimukhtar.phonepayment.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.rifqimukhtar.phonepayment.R
import com.rifqimukhtar.phonepayment.db.entity.User
import com.rifqimukhtar.phonepayment.rest.ApiClient
import com.rifqimukhtar.phonepayment.rest.ApiInteface
import kotlinx.android.synthetic.main.activity_main_menu.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainMenuActivity : AppCompatActivity() {
    companion object val API_KEY = "xxxxxx"
    val currentUserID = 1
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_menu)
        buttonGroup()
       // getUser()
    }

    private fun getUser() {
        val apiCall = ApiClient.getClient(API_KEY, applicationContext)?.create(ApiInteface::class.java)?.getUser(currentUserID)
        apiCall?.enqueue(object : Callback<User> {
            override fun onResponse(call: Call<User>, response: Response<User>) {
                val item = response.body()!!
                val user = User(item.status, item.name, item.phoneNumber, item.email, item.balance)
                setUserDetail(user)
            }

            override fun onFailure(call: Call<User>, t: Throwable) {
                Toast.makeText(applicationContext, "Request Failed", Toast.LENGTH_SHORT).show()
                Log.d("Failed", t.message)
            }
        })
    }

    private fun setUserDetail(user: User) {
        tvWelcomeUser.text = "Weclome, ${user.name}"
        tvUserBalance.text = "Rp ${user.balance}"
    }

    private fun buttonGroup() {
        btnHistory.setOnClickListener {
            startActivity(Intent(this, HistoryActivity::class.java))
        }

        btnTelkomOption.setOnClickListener {
            startActivity(Intent(this, TelkomPaymentActivity::class.java))
        }
        cvUserProfile.setOnClickListener {
            startActivity(Intent(this, ProfileActivity::class.java))
        }
    }
}
