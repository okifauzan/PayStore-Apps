package com.rifqimukhtar.phonepayment.activities

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.widget.Toast
import com.rifqimukhtar.phonepayment.R
import com.rifqimukhtar.phonepayment.db.entity.BaseResponse
import com.rifqimukhtar.phonepayment.db.entity.SendOTP
import com.rifqimukhtar.phonepayment.db.entity.SendOTPResponse
import com.rifqimukhtar.phonepayment.db.entity.SendRequestPayment
import com.rifqimukhtar.phonepayment.rest.ApiClient
import com.rifqimukhtar.phonepayment.rest.ApiInteface
import kotlinx.android.synthetic.main.activity_payment_verification.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PaymentVerification : AppCompatActivity() {

    var timerAvailable: Boolean = true
    var getOTP: String? = null
    var sendRequestPayment: SendRequestPayment? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_payment_verification)
        if (intent.extras != null){
            val bundle = intent.extras
            sendRequestPayment = bundle?.getSerializable("sendRequestPayment") as SendRequestPayment
//            val idBill = bundle?.getInt("idBill")
//            val idMethod = bundle?.getInt("idMethod")
//            val idUser = bundle?.getInt("idUser")
            //sendRequestPayment = SendRequestPayment(idBill, idUser, idMethod)
            getOTP = bundle?.getString("otp")
            Log.d("bundle", getOTP)
            //Log.d("State", "Payment Verification $idBill, $idUser, $idMethod")
        }
        timerCount()
        onClickGroup()
    }

    fun timerCount(){
        val timer = object : CountDownTimer(30000, 1000){
            override fun onTick(millisUntilFinished: Long) {
                tvTimerPayOTP.setText("OTP valid for ${millisUntilFinished/1000} seconds")
                btnResendPayOtp.isEnabled = false
                btnResendPayOtp.setTextColor(Color.RED)
                timerAvailable = true
            }

            override fun onFinish() {
                btnResendPayOtp.isEnabled = true
                btnResendPayOtp.setTextColor(Color.BLUE)
                timerAvailable = false
            }
        }.start()
    }

    private fun onClickGroup() {
        btnResendPayOtp.setOnClickListener {
            val preference = getSharedPreferences("Pref_Profile2", 0)
            val emailOTP = preference.getString("PREF_EMAIL", "")
            val sendOtpModel = SendOTP("+6287883445469", emailOTP)
            val sendOtpCall = ApiClient.getClient()?.create(ApiInteface::class.java)?.postOTP(sendOtpModel)
            sendOtpCall?.enqueue(object : Callback<SendOTPResponse> {
                override fun onResponse(call: Call<SendOTPResponse>, response: Response<SendOTPResponse>) {
                    timerCount()
                    btnResendPayOtp.isEnabled = false
                    btnResendPayOtp.setTextColor(Color.RED)
                    val otp = response.body()!!.otp
                    getOTP = otp
                    Log.d("otp", getOTP)
                }

                override fun onFailure(call: Call<SendOTPResponse>, t: Throwable) {
                    Toast.makeText(applicationContext, "Failed to send OTP", Toast.LENGTH_SHORT).show()
                    Log.d("Failed", t.message)
                }
            })
        }

        btnPayConfirmOTP.setOnClickListener {
            var textOTP = etPayOTPNumber.text.toString()
            if ((textOTP == getOTP) && timerAvailable){
                Log.d("State", "${sendRequestPayment!!.idBill} +${sendRequestPayment!!.idPaymentMethod} + ${sendRequestPayment!!.idUser}")
                val paymentModel = sendRequestPayment
                val paymentCall = ApiClient.getClient()?.create(ApiInteface::class.java)?.verifyRequest(paymentModel!!)
                paymentCall?.enqueue(object : Callback<BaseResponse<Any>>{
                    override fun onResponse(call: Call<BaseResponse<Any>>, response: Response<BaseResponse<Any>>) {
                        if(response.isSuccessful){
                            Toast.makeText(applicationContext, "Bayar Sukses", Toast.LENGTH_LONG).show()
                            finish()
                        } else {
                            Toast.makeText(applicationContext, "Pembayaran Gagal", Toast.LENGTH_LONG).show()
                        }
                    }

                    override fun onFailure(call: Call<BaseResponse<Any>>, t: Throwable) {
                        Toast.makeText(applicationContext, "Server not Respond", Toast.LENGTH_LONG).show()
                    }
                })
            }
        }
    }
}
