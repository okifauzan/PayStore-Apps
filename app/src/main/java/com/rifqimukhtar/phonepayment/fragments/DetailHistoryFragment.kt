package com.rifqimukhtar.phonepayment.fragments

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.Observer
import com.rifqimukhtar.phonepayment.R
import com.rifqimukhtar.phonepayment.activities.HistoryActivity
import com.rifqimukhtar.phonepayment.activities.MainActivity
import com.rifqimukhtar.phonepayment.activities.PaymentVerification
import com.rifqimukhtar.phonepayment.activities.TelkomPaymentActivity
import com.rifqimukhtar.phonepayment.db.entity.*
import com.rifqimukhtar.phonepayment.rest.ApiClient
import com.rifqimukhtar.phonepayment.rest.ApiInteface
import com.rifqimukhtar.phonepayment.viewmodel.BillViewModel
import kotlinx.android.synthetic.main.activity_main_menu.*
import kotlinx.android.synthetic.main.fragment_detail_bill.*
import kotlinx.android.synthetic.main.fragment_detail_history.*
import org.koin.android.ext.android.inject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


/**
 * A simple [Fragment] subclass.
 */
class DetailHistoryFragment : Fragment() {
    private val billViewModel: BillViewModel by inject()
    var textUri:String = ""
    var history:BillHistory? = null
    var method: PaymentMethod? = null
    var paymentModel: SendRequestPayment? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_detail_history, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if(arguments != null)
        {
            //get selected method
            textUri = arguments!!.getString("photoUri").toString()
            history = arguments!!.getSerializable("selectedMethod") as BillHistory
            Log.d("State", history!!.amount.toString())
            updateSelectedMethod(history)
            updateDetailUI(history!!)
        }
        buttonGroup()
    }

    private fun updateDetailUI(history: BillHistory) {

        val adminFee = 0

        tvPhotoURI.text = textUri

        tvStatusDetailHistory.text = history.status
        tvNamaHistory.text = history.name
        tvNominalHistory.text = history.amount.toString()
        tvAdminHistory.text = adminFee.toString()
        tvTotalHistory.text = history.amount?.plus(adminFee).toString()
    }

    private fun buttonGroup() {
        ibBackFromDetailHistory.setOnClickListener {
            (activity as HistoryActivity).showListHitoryFragment()
        }
        btnUploadPhoto.setOnClickListener {
            //startActivity(Intent(activity, MainActivity::class.java))
           // (activity as HistoryActivity).showCamera()
            val i = Intent(activity, MainActivity::class.java)
            startActivityForResult(i, 1)
        }

        btnBayarTagihanHistory.setOnClickListener {
            sendHistoryRequestPayment()
        }

    }

    private fun sendHistoryRequestPayment() {
        val preference = activity!!.getSharedPreferences("Pref_Profile", 0)
        val userId = preference.getInt("PREF_USERID", 0)
        Log.d("State", "send request payment ${method?.methodName}")
        paymentModel = SendRequestPayment(history!!.idBill,userId,method?.idPaymentMethod)
        val paymentCall = ApiClient.getClient()?.create(ApiInteface::class.java)?.verifyRequest(paymentModel!!)
        paymentCall?.enqueue(object : Callback<BaseResponse<Any>> {
            override fun onResponse(call: Call<BaseResponse<Any>>, response: Response<BaseResponse<Any>>) {
                if(response.isSuccessful){
                    if(method?.idPaymentMethod == 1){
                        Log.d("State", "otw otp ${method!!.idPaymentMethod} ${method!!.methodName}")
                        otpPayment()
                    } else{
                        Toast.makeText( activity, "Bayar Sukses", Toast.LENGTH_LONG).show()
                        showSuccessDialog()
                    }
                    btnBayarTagihanHistory.visibility = View.GONE
                    tvStatusDetailHistory.text = "paid"
                } else {
                    Toast.makeText(activity, "Pembayaran Gagal", Toast.LENGTH_LONG).show()
                }
            }
            override fun onFailure(call: Call<BaseResponse<Any>>, t: Throwable) {
                Toast.makeText(activity, "Server not Respond", Toast.LENGTH_LONG).show()
            }
        })

    }

    fun updateSelectedMethod(history: BillHistory?) {
        if (history != null) {
            if (history.status == "paid"){
                btnUploadPhoto.visibility = View.GONE
                btnBayarTagihanHistory.visibility = View.GONE
            } else{
                if(history.idPaymentMethod == 1)
                {
                    btnBayarTagihanHistory.visibility = View.VISIBLE
                    method = PaymentMethod(R.drawable.ic_wallet, "PayStore Wallet", history.amount.toString(), 1)

                } else
                {
                    btnUploadPhoto.visibility = View.VISIBLE
                    method = PaymentMethod(R.drawable.ic_virtual_acc, "Virtual Account", history.amount.toString(), 2)
                }
                ivSelectedMethodHistory.setImageResource(method?.image!!)
                tvSelectedTitleMethodHistory.text = method?.methodName
                tvSelectedValueMethodHistory.text = method?.methodValue
            }
        }
    }

    @Override
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                val returnString = data!!.getStringExtra("result")
                tvPhotoURI.text = returnString
                btnUploadPhoto.visibility = View.GONE
                btnBayarTagihanHistory.visibility = View.VISIBLE
            }
        }
    }

    private fun otpPayment() {
        val preference = activity!!.getSharedPreferences("Pref_Profile2", 0)
        val emailOTP = preference.getString("PREF_EMAIL", "")
        val sendOtpModel = SendOTP("+6287883445469", emailOTP)
        Log.d("State", "OTW payment ${history!!.idBill} , ${method!!.idPaymentMethod}, iduser")
        val sendOtpCall = ApiClient.getClient()?.create(ApiInteface::class.java)?.postOTP(sendOtpModel)
        sendOtpCall?.enqueue(object : Callback<SendOTPResponse> {
            override fun onResponse(call: Call<SendOTPResponse>, response: Response<SendOTPResponse>) {
                if(response.isSuccessful){
                    Log.d("State", "Sending OTP payment")
                    val otp = response.body()!!.otp
                    Log.d("otp", otp.toString())
                    val bundle = Bundle()
                    bundle.putString("otp", otp.toString())
                    bundle.putSerializable("sendRequestPayment", paymentModel)
                    //TODO("Delete when done debugging")
                    Toast.makeText(activity, otp.toString(), Toast.LENGTH_LONG).show()
                    val intent = Intent(activity, PaymentVerification::class.java)
                    intent.putExtras(bundle)
                    startActivity(intent)
                } else {
                    Toast.makeText(context, "OTP not available now", Toast.LENGTH_SHORT).show()
                    Log.d("otp", "gagal")
                }
            }

            override fun onFailure(call: Call<SendOTPResponse>, t: Throwable) {
                Toast.makeText(context, "Failed to send OTP", Toast.LENGTH_SHORT).show()
                Log.d("Failed", t.message)
            }
        })
    }

    private fun showSuccessDialog() {
        val dialogFragment = PaymentResultFragment()
        var ft: FragmentTransaction = activity!!.supportFragmentManager.beginTransaction()
        ft?.addToBackStack(null)
        val bundle=Bundle()
        bundle.putBoolean("isHistory", true)
        dialogFragment.arguments = bundle
        dialogFragment.show(ft!!, "dialog")
    }

}
