package com.rifqimukhtar.phonepayment.fragments

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
import com.rifqimukhtar.phonepayment.activities.PaymentVerification
import com.rifqimukhtar.phonepayment.activities.TelkomPaymentActivity
import com.rifqimukhtar.phonepayment.db.entity.*
import com.rifqimukhtar.phonepayment.viewmodel.BillViewModel
import com.rifqimukhtar.phonepayment.viewmodel.UserViewModel
import kotlinx.android.synthetic.main.fragment_detail_bill.*
import org.koin.android.ext.android.inject
import com.rifqimukhtar.phonepayment.rest.ApiClient
import com.rifqimukhtar.phonepayment.rest.ApiInteface
import kotlinx.android.synthetic.main.activity_main_menu.*
import kotlinx.android.synthetic.main.fragment_detail_bill.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailBillFragment : Fragment() {
    private val billViewModel: BillViewModel by inject()
    private var isEnoughBalance : Boolean? = null
    var currentBill:PhoneBill? =null
    var method:PaymentMethod? = null
    var virtualNumber:String = "8001"
    var balance:Int = 0
    var sendRequesPayment: SendRequestPayment? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_detail_bill, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if(arguments != null)
        {
            //get selected method
            method = arguments!!.getSerializable("selectedMethod") as PaymentMethod
            currentBill = arguments!!.getSerializable("currentBill") as PhoneBill
            virtualNumber = "8001${currentBill?.telephoneNumber}"
            balance = arguments!!.getInt("balance", 0)
            Log.d("State", method?.methodName)
            updateSelectedMethod(method)
            setBillDetail(currentBill!!)
        }
        buttonGroup()
    }
    private fun buttonGroup() {
        btnMetodeOption.setOnClickListener {
            showPaymentMethod()
        }

        btnBayarTagihan.setOnClickListener {
            // TODO("call bayar tagihan api") done!!!! kurang dicek bug
            sendPaymentRequest()
        }
        ibBackFromDetail.setOnClickListener {
            (activity as TelkomPaymentActivity).showInsertNumberFragment()
        }
    }

    fun updateSelectedMethod(paymentMethod: PaymentMethod?) {
        if (paymentMethod != null) {
            method = paymentMethod
            ivSelectedMethod.setImageResource(method?.image!!)
            tvSelectedTitleMethod.text = method?.methodName
            if (method?.idPaymentMethod == 1){
                tvSelectedValueMethod.text = "Rp $balance"
            }
            tvSelectedValueMethod.text = method?.methodValue
            isEnoughBalance = method?.isEnoughBalance
        }
    }

    private fun setBillDetail(phoneBill: PhoneBill) {
        val adminFee = 0
        val total = phoneBill.amount?.plus(adminFee)

        tvNama.text = phoneBill.telephoneOwner
        tvNominal.text = phoneBill.amount.toString()
        tvAdmin.text = adminFee.toString()
        tvTotal.text = total.toString()
    }

    private fun sendPaymentRequest() {
        val preference = activity!!.getSharedPreferences("Pref_Profile", 0)
        val userId = preference.getInt("PREF_USERID", 0)
        Log.d("State", "send request payment ${method?.methodName}")
        sendRequesPayment = SendRequestPayment(currentBill?.idBill,userId,method?.idPaymentMethod)
        billViewModel.sendPaymentRequest(sendRequesPayment!!).observe(activity as TelkomPaymentActivity, Observer<String> {
        })
        if(method?.idPaymentMethod == 1){
            Log.d("State", "otw otp ${method!!.idPaymentMethod} ${method!!.methodName}")
            otpPayment()
        } else{
            showSuccessDialog()
            Toast.makeText(context, "Request Success! Please finish your transaction by ATM transfer", Toast.LENGTH_LONG).show()
            removeFragment()
        }
    }

    private fun showPaymentMethod() {
        val dialogFragment = PaymentMethodFragment()
        val bundle = Bundle()
        balance.let { bundle.putInt("balanceAmount", it) }
        isEnoughBalance?.let { bundle.putBoolean("isEnoughBalance", it) }
        virtualNumber?.let { bundle.putString("virtualNumber", it) }
        dialogFragment.arguments = bundle

        var ft: FragmentTransaction = activity!!.supportFragmentManager.beginTransaction()
        ft?.addToBackStack(null)
        dialogFragment.show(ft!!, "dialog")
    }

    private fun otpPayment() {
        val preference = activity!!.getSharedPreferences("Pref_Profile2", 0)
        val emailOTP = preference.getString("PREF_EMAIL", "")
        val sendOtpModel = SendOTP("+6287883445469", emailOTP)
        Log.d("State", "OTW payment ${currentBill!!.idBill} , ${method!!.idPaymentMethod}, iduser")
        val sendOtpCall = ApiClient.getClient()?.create(ApiInteface::class.java)?.postOTP(sendOtpModel)
        sendOtpCall?.enqueue(object : Callback<SendOTPResponse> {
            override fun onResponse(call: Call<SendOTPResponse>, response: Response<SendOTPResponse>) {
                if(response.isSuccessful){
                    Log.d("State", "Sending OTP payment")
                    val otp = response.body()!!.otp
                    Log.d("otp", otp.toString())
                    val bundle = Bundle()
                    bundle.putString("otp", otp.toString())
                    bundle.putSerializable("sendRequestPayment", sendRequesPayment)
                    //TODO("Delete when done debugging")
                    Toast.makeText(activity, otp.toString(), Toast.LENGTH_LONG).show()
                    val intent = Intent(activity, PaymentVerification::class.java)
                    intent.putExtras(bundle)
                    startActivity(intent)
                    removeFragment()
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

    private fun removeFragment() {
        (activity as TelkomPaymentActivity).showInsertNumberFragment()
    }
    private fun showSuccessDialog() {
        val dialogFragment = PaymentResultFragment()
        var ft: FragmentTransaction = activity!!.supportFragmentManager.beginTransaction()
        ft?.addToBackStack(null)
        dialogFragment.show(ft!!, "dialog")
    }
}
