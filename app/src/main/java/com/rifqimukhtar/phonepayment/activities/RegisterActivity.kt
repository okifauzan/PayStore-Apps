package com.rifqimukhtar.phonepayment.activities

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.Toast
import com.rifqimukhtar.phonepayment.R
import com.rifqimukhtar.phonepayment.db.entity.CreateAccount
import com.rifqimukhtar.phonepayment.db.entity.SendOTP
import com.rifqimukhtar.phonepayment.db.entity.SendOTPResponse
import com.rifqimukhtar.phonepayment.rest.ApiClient
import com.rifqimukhtar.phonepayment.rest.ApiInteface
import kotlinx.android.synthetic.main.activity_register.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RegisterActivity : AppCompatActivity() {

    companion object val API_KEY = "xxxxxx"
    val patternName = "^[a-zA-Z\\s]{3,20}\$".toRegex()
    val patternEmail = "^([a-z0-9])+([_.-]?[a-z0-9]+)*@[a-z]+([.-]?[a-z]+)*(\\.[a-z]{2,3})+\$".toRegex() //recipient name + @ + domain + . + top level domain
    val patternHandphone = "^[0-9]{9,12}\$".toRegex()
    val patternPassword = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#\$%!.,_?&])(?=\\S+\$).{8,20}\$".toRegex()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        onClickGroup()
    }

    fun onClickGroup(){
        btnRegister.setOnClickListener {
            var isCorrect: Boolean = true
            tvErrorRegistNama.visibility = View.GONE
            tvErrorRegistEmail.visibility = View.GONE
            tvErrorRegistHandphone.visibility = View.GONE
            tvErrorRegistPassword.visibility = View.GONE
            tvErrorRepeatPassword.visibility = View.GONE

            var textName = etRegistNama.text.toString()
            var textEmail = etRegistEmail.text.toString()
            var textHandphone = etRegistHandphone.text.toString()
            var textPassword = etRegistPassword.text.toString()
            var textRepeatPassword = etRegistRepeatPassword.text.toString()

            if (!(patternName.matches(textName))){
                tvErrorRegistNama.visibility = View.VISIBLE
                isCorrect = false
                Log.d("test", "Wrong Name Format")
            }
            if (!(patternEmail.matches(textEmail))){
                tvErrorRegistEmail.visibility = View.VISIBLE
                isCorrect = false
                Log.d("test", "Wrong Email Format")
            }
            if (!(patternHandphone.matches(textHandphone))){
                tvErrorRegistHandphone.visibility = View.VISIBLE
                isCorrect = false
                Log.d("test", "Wrong Number Format")
            }
            if (!(patternPassword.matches(textPassword))){
                tvErrorRegistPassword.visibility = View.VISIBLE
                isCorrect = false
                Log.d("test", "Wrong Password Format")
            }
            if (!(textRepeatPassword.equals(textPassword))){
                tvErrorRepeatPassword.visibility = View.VISIBLE
                isCorrect = false
                Log.d("test", "Password didn't matches")
            }

            if (isCorrect) {
                val sendOtpModel = SendOTP("+6287883445469", textEmail)
                val sendOtpCall = ApiClient.getClient()?.create(ApiInteface::class.java)?.postOTP(sendOtpModel)
                sendOtpCall?.enqueue(object : Callback<SendOTPResponse>{
                    override fun onResponse(call: Call<SendOTPResponse>, response: Response<SendOTPResponse>) {
                        if(response.isSuccessful){
                            val otp = response.body()!!.otp
                            Log.d("otp", otp.toString())
                            val bundle = Bundle()
                            bundle.putString("name", textName)
                            bundle.putString("email", textEmail)
                            bundle.putString("phoneNumber", textHandphone)
                            bundle.putString("password", textPassword)
                            bundle.putString("otp", otp.toString())
                            Toast.makeText(applicationContext, otp.toString(), Toast.LENGTH_LONG).show()
                            val intent = Intent(this@RegisterActivity, RegisterVerification::class.java)
                            intent.putExtras(bundle)
                            startActivity(intent)
                        } else {
                            Toast.makeText(applicationContext, "OTP not available now", Toast.LENGTH_SHORT).show()
                            Log.d("otp", "gagal")
                        }
                    }

                    override fun onFailure(call: Call<SendOTPResponse>, t: Throwable) {
                        Toast.makeText(applicationContext, "Failed to send OTP", Toast.LENGTH_SHORT).show()
                        Log.d("Failed", t.message!!)
                    }
                })
            } else {
                Toast.makeText(applicationContext, "Please Enter Valid Information", Toast.LENGTH_LONG).show()
            }
        }
        btnRegisterBack.setOnClickListener {
            startActivity(Intent(this@RegisterActivity, LoginActivity::class.java))
        }
    }
}
