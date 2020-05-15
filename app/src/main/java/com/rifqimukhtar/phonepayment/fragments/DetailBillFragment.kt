package com.rifqimukhtar.phonepayment.fragments

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.Observer
import com.rifqimukhtar.phonepayment.R
import com.rifqimukhtar.phonepayment.activities.TelkomPaymentActivity
import com.rifqimukhtar.phonepayment.db.entity.*
import com.rifqimukhtar.phonepayment.viewmodel.BillViewModel
import com.rifqimukhtar.phonepayment.viewmodel.UserViewModel
import kotlinx.android.synthetic.main.fragment_detail_bill.*
import org.koin.android.ext.android.inject

class DetailBillFragment : Fragment() {
    private val billViewModel: BillViewModel by inject()
    private val userViewModel: UserViewModel by inject()
    private var isEnoughBalance : Boolean? = null
    var currentBill:PhoneBill? =null
    var method:PaymentMethod? = null
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
            showSuccessDialog()
        }
        ibBackFromDetail.setOnClickListener {
            (activity as TelkomPaymentActivity).showInsertNumberFragment()
        }
    }

    private fun sendPaymentRequest() {
        sendRequesPayment = SendRequestPayment(currentBill?.idBill,1,method?.idPaymentMethod)
        billViewModel.sendPaymentRequest(sendRequesPayment!!).observe(activity as TelkomPaymentActivity, Observer<String> {

            Log.d("State", "send request payment $it")
        })
    }


    fun updateSelectedMethod(method: PaymentMethod?) {
        if (method != null) {
            ivSelectedMethod.setImageResource(method.image!!)
            tvSelectedTitleMethod.text = method.methodName
            //TODO("add real user balance")
            tvSelectedValueMethod.text = method.methodValue
            isEnoughBalance = method.isEnoughBalance
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

    private fun showPaymentMethod() {
        val dialogFragment = PaymentMethodFragment()
        val bundle = Bundle()
        isEnoughBalance?.let { bundle.putBoolean("isEnoughBalance", it) }
        method!!.methodValue?.let { bundle.putString("balance_amount", it) }
        dialogFragment.arguments = bundle

        var ft: FragmentTransaction = activity!!.supportFragmentManager.beginTransaction()
        ft?.addToBackStack(null)
        dialogFragment.show(ft!!, "dialog")
    }
    private fun showSuccessDialog() {
        (activity as TelkomPaymentActivity).showInsertNumberFragment()
        val dialogFragment = PaymentResultFragment()
        var ft: FragmentTransaction = activity!!.supportFragmentManager.beginTransaction()
        ft?.addToBackStack(null)
        dialogFragment.show(ft!!, "dialog")
    }

    fun sendBundle()
    {
        val bundle = Bundle()
        bundle.putSerializable("sendRequestPayment", sendRequesPayment)

        val intent = Intent(activity, PaymentVerification::class.java)
        intent.putExtras(bundle)
        startActivity(intent)
    }
}
