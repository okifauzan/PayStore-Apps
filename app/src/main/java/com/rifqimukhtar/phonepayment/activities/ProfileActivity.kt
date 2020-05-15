package com.rifqimukhtar.phonepayment.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.rifqimukhtar.phonepayment.R
import com.rifqimukhtar.phonepayment.db.entity.Logout
import com.rifqimukhtar.phonepayment.db.entity.LogoutResponse
import com.rifqimukhtar.phonepayment.db.entity.SendUser
import com.rifqimukhtar.phonepayment.db.entity.User
import com.rifqimukhtar.phonepayment.rest.ApiClient
import com.rifqimukhtar.phonepayment.rest.ApiInteface
import kotlinx.android.synthetic.main.activity_profile.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProfileActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        if (intent.extras != null) {
            val bundle = intent.extras
            val user = bundle?.getSerializable("currentUser") as User
            user.phoneNumber
            setUserDetail(user)
        }

        buttonGroup()
    }

    private fun setUserDetail(user:User) {
        tvUserName.text = user.name
        tvUserEmail.text = user.email
        tvUserPhone.text = user.phoneNumber
        tvUserValueWallet.text = user.balance.toString()
    }

    private fun buttonGroup() {
        ibBackFromProfil.setOnClickListener {
            startActivity(Intent(this, MainMenuActivity::class.java))
        }
        btnLogout.setOnClickListener {
            val logout = Logout(1)
            val apiCall = ApiClient.getClient()?.create(ApiInteface::class.java)
            apiCall?.postLogout(logout)?.enqueue(object : Callback<LogoutResponse>{
                override fun onResponse(call: Call<LogoutResponse>, response: Response<LogoutResponse>) {
                    if (response.isSuccessful){
                        val preference = getSharedPreferences("Pref_Profile", 0)
                        val editor = preference.edit()
                        editor.putBoolean("PREF_ISLOGIN", false)
                        editor.apply()
                        Log.d("logout", response.body().toString())
                        startActivity(Intent(this@ProfileActivity, LoginActivity::class.java))
                    } else {
                        Toast.makeText(applicationContext, "Can't logout right now", Toast.LENGTH_SHORT).show()
                        Log.d("logout", response.toString())
                    }
                }
                override fun onFailure(call: Call<LogoutResponse>, t: Throwable) {
                    Toast.makeText(applicationContext, "Server not responding", Toast.LENGTH_SHORT).show()
                }
            })
            val preference = getSharedPreferences("Pref_Profile", 0)
            val editor = preference.edit()
            editor.putBoolean("PREF_ISLOGIN", false)
            editor.apply()
            startActivity(Intent(this@ProfileActivity, LoginActivity::class.java))
        }
    }
}
