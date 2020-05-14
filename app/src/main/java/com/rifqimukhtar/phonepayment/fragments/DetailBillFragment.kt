package com.rifqimukhtar.phonepayment.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.rifqimukhtar.phonepayment.R
import com.rifqimukhtar.phonepayment.activities.TelkomPaymentActivity
import com.rifqimukhtar.phonepayment.db.entity.PaymentMethod
import kotlinx.android.synthetic.main.fragment_detail_bill.*

class DetailBillFragment : Fragment() {

    private var isEnoughBalance : Boolean? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_detail_bill, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val method = (activity as TelkomPaymentActivity).getSelectedMethod()
        updateSelectedMethod(method)
        buttonGroup()
    }
    private fun buttonGroup() {
        btnMetodeOption.setOnClickListener {
            showPaymentMethod()
        }

        btnBayarTagihan.setOnClickListener {
            // TODO("call bayar tagihan api")
            showSuccessDialog()
        }
        ibBackFromDetail.setOnClickListener {
            (activity as TelkomPaymentActivity).addInsertNumberFragment()
        }
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
        val dialogFragment = PaymentResultFragment()
        var ft: FragmentTransaction = activity!!.supportFragmentManager.beginTransaction()
        ft?.addToBackStack(null)
        dialogFragment.show(ft!!, "dialog")
    }
}
