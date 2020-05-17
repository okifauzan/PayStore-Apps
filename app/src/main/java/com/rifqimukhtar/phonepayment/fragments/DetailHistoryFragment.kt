package com.rifqimukhtar.phonepayment.fragments

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.rifqimukhtar.phonepayment.R
import com.rifqimukhtar.phonepayment.activities.HistoryActivity
import com.rifqimukhtar.phonepayment.activities.MainActivity
import com.rifqimukhtar.phonepayment.db.entity.BillHistory
import com.rifqimukhtar.phonepayment.db.entity.PaymentMethod
import kotlinx.android.synthetic.main.fragment_detail_history.*


/**
 * A simple [Fragment] subclass.
 */
class DetailHistoryFragment : Fragment() {

    var textUri:String = ""
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
            val history = arguments!!.getSerializable("selectedMethod") as BillHistory
            Log.d("State", history.amount.toString())
            updateSelectedMethod(history)
            updateDetailUI(history)
        }
        buttonGroup()
    }

    private fun updateDetailUI(history: BillHistory) {

        val adminFee = 0

        tvPhoto.text = textUri

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

        btnBayarTagihan.setOnClickListener {
            Toast.makeText(activity, "Tagihan Terkirim", Toast.LENGTH_SHORT).show()
            tvStatusDetailHistory.text = "paid"
        }

    }

    fun updateSelectedMethod(history: BillHistory?) {
        if (history != null) {
            val method: PaymentMethod
            if(history.idPaymentMethod == 1)
            {

                method = PaymentMethod(R.drawable.ic_wallet, "PayStore Wallet", history.amount.toString(), true,1)

            } else
            {
                method = PaymentMethod(R.drawable.ic_virtual_acc, "Virtual Account", history.amount.toString(), false,1)
            }
            ivSelectedMethodHistory.setImageResource(method.image!!)
            tvSelectedTitleMethodHistory.text = method.methodName
            tvSelectedValueMethodHistory.text = method.methodValue
        }
    }

    @Override
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                val returnString = data!!.getStringExtra("result")
                tvPhoto.text = returnString
                btnUploadPhoto.visibility = View.GONE
                btnBayarTagihan.visibility = View.VISIBLE
            }
        }
    }
}
