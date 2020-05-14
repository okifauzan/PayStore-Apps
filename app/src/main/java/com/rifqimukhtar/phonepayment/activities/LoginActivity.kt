package com.rifqimukhtar.phonepayment.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.rifqimukhtar.phonepayment.R
import com.rifqimukhtar.phonepayment.db.entity.Login
import com.rifqimukhtar.phonepayment.db.entity.LoginResponse
import com.rifqimukhtar.phonepayment.rest.ApiClient
import com.rifqimukhtar.phonepayment.rest.ApiInteface
import kotlinx.android.synthetic.main.activity_login.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginActivity : AppCompatActivity() {

    companion object val API_KEY = "xxxxxx"

    val patternLoginNumber = "^[0-9]{9,12}\$".toRegex()
    val patternLoginPass = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#\$%!_?&])(?=\\S+\$).{8,20}\$".toRegex()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        onClickGroup()
    }

    fun onClickGroup(){
        btnLogin.setOnClickListener {
            var textLoginNumber = etLoginHandphone.text.toString()
            var textLoginPass = etLoginPassword.text.toString()
            if (patternLoginNumber.matches(textLoginNumber) && patternLoginPass.matches(textLoginPass)){
                val loginModel = Login(textLoginNumber, textLoginPass)
                val loginCall = ApiClient.getClient(API_KEY, this)?.create(ApiInteface::class.java)?.postLogin(loginModel)
                loginCall?.enqueue(object : Callback<LoginResponse>{
                    override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                        if (response.isSuccessful){
                            val preference = getSharedPreferences("Pref_Profile", 0)
                            val editor = preference.edit()
                            editor.putBoolean("PREF_ISLOGIN", true)
                            editor.apply()
                            Toast.makeText(applicationContext, "Login Success", Toast.LENGTH_SHORT).show()
                            Log.d("Login", "Success")
                            startActivity(Intent(this@LoginActivity, MainMenuActivity::class.java))
                        } else {
                            Toast.makeText(applicationContext, "Wrong Phone/Password", Toast.LENGTH_SHORT).show()
                            Log.d("Response", response.toString())
                        }
                    }

                    override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                        Log.d("Login", t.message)
                        Toast.makeText(applicationContext, "Failed to Login", Toast.LENGTH_SHORT).show()
                    }
                })
            } else {
                Toast.makeText(applicationContext, "Wrong Phone/Pass Format", Toast.LENGTH_SHORT).show()
                Log.d("Login", "Wrong Format")
            }
        }

        btnToRegister.setOnClickListener {
            startActivity(Intent(this@LoginActivity, RegisterActivity::class.java))
        }
    }
}
