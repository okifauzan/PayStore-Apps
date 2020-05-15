package com.rifqimukhtar.phonepayment.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.rifqimukhtar.phonepayment.R
import com.rifqimukhtar.phonepayment.activities.TelkomPaymentActivity
import com.rifqimukhtar.phonepayment.db.entity.PaymentMethod
import com.rifqimukhtar.phonepayment.db.entity.PhoneBill
import com.rifqimukhtar.phonepayment.db.entity.SendUser
import kotlinx.android.synthetic.main.fragment_detail_bill.*

class DetailBillFragment : Fragment() {

    private var isEnoughBalance : Boolean? = null
    var currentBill:PhoneBill? =null

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
            val method = arguments!!.getSerializable("selectedMethod") as PaymentMethod
            currentBill = arguments!!.getSerializable("currentBill") as PhoneBill
            Log.d("State", method.methodName)
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
            // TODO("call bayar tagihan api")
            sendPaymentRequest()
            showSuccessDialog()
        }
        ibBackFromDetail.setOnClickListener {
            (activity as TelkomPaymentActivity).showInsertNumberFragment()
        }
    }

    private fun sendPaymentRequest() {

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
}
