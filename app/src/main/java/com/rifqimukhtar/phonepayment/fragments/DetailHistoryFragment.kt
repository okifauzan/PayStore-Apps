package com.rifqimukhtar.phonepayment.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.rifqimukhtar.phonepayment.R
import com.rifqimukhtar.phonepayment.activities.HistoryActivity
import com.rifqimukhtar.phonepayment.db.entity.BillHistory
import com.rifqimukhtar.phonepayment.db.entity.PaymentMethod
import kotlinx.android.synthetic.main.fragment_detail_history.*

/**
 * A simple [Fragment] subclass.
 */
class DetailHistoryFragment : Fragment() {

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
            val history = arguments!!.getSerializable("selectedMethod") as BillHistory
            Log.d("State", history.amount.toString())
            updateSelectedMethod(history)
            updateDetailUI(history)
        }
        buttonGroup()
    }

    private fun updateDetailUI(history: BillHistory) {

        val adminFee = 0

        tvNamaHistory.text = history.name
        tvNominalHistory.text = history.amount.toString()
        tvAdminHistory.text = adminFee.toString()
        tvTotalHistory.text = history.amount?.plus(adminFee).toString()
    }

    private fun buttonGroup() {

        ibBackFromDetailHistory.setOnClickListener {
            (activity as HistoryActivity).showListHitoryFragment()
        }
    }

    fun updateSelectedMethod(history: BillHistory?) {
        if (history != null) {
            val method: PaymentMethod
            if(history.idPaymentMethod == 1)
            {

                method = PaymentMethod(R.drawable.ic_wallet, "PayStore Wallet", history.amount.toString(), true)

            } else
            {
                method = PaymentMethod(R.drawable.ic_virtual_acc, "Virtual Account", history.amount.toString(), false)
            }
            ivSelectedMethodHistory.setImageResource(method.image!!)
            tvSelectedTitleMethodHistory.text = method.methodName
            tvSelectedValueMethodHistory.text = method.methodValue
        }
    }

}
