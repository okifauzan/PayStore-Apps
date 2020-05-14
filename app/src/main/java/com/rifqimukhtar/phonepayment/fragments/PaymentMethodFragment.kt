package com.rifqimukhtar.phonepayment.fragments

import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.rifqimukhtar.phonepayment.R
import com.rifqimukhtar.phonepayment.activities.DetailBillFragment
import com.rifqimukhtar.phonepayment.activities.TelkomPaymentActivity
import com.rifqimukhtar.phonepayment.adapters.PaymentMethodAdapter
import com.rifqimukhtar.phonepayment.db.entity.PaymentMethod
import kotlinx.android.synthetic.main.fragment_payment_method.*


/**
 * A simple [Fragment] subclass.
 */
class PaymentMethodFragment : DialogFragment() {

    var listPaymentMethod: ArrayList<PaymentMethod> = ArrayList()
    private lateinit var adapter: PaymentMethodAdapter
    private var isEnoughBalance: Boolean = true
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        adapter = PaymentMethodAdapter()
        adapter.notifyDataSetChanged()
        // Inflate the layout for this fragment
        dialog!!.window!!.setGravity(Gravity.FILL_HORIZONTAL or Gravity.BOTTOM)
        val p = dialog!!.window!!.attributes
        p.width = ViewGroup.LayoutParams.MATCH_PARENT
        p.softInputMode = WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE
        return inflater.inflate(R.layout.fragment_payment_method, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        checkBalance()
        generatePaymentMethod()
        setupRecyclerView()
        buttonGroup()
    }

    private fun checkBalance() {
        if(arguments != null)
        {
            //set isEnoughBalance
            isEnoughBalance = arguments!!.getBoolean("isEnoughBalance")
            Log.d("State", isEnoughBalance.toString())
        }
    }

    private fun generatePaymentMethod() {
        val eWallet = PaymentMethod(R.drawable.ic_wallet, "PayStore Wallet", "Rp 50.000", isEnoughBalance )
        val virtualAcc = PaymentMethod(R.drawable.ic_virtual_acc, "Virtual Account", "088888888888888", true)
        listPaymentMethod.add(eWallet)
        listPaymentMethod.add(virtualAcc)
    }

    private fun buttonGroup() {
        btnBackFromMetode.setOnClickListener {
            dismiss()
        }
        ibCloseFromMetode.setOnClickListener{
            dismiss()
        }
    }

    private fun setupRecyclerView() {
        adapter.setItem(listPaymentMethod)
        adapter.setOnItemClickCallback(object : PaymentMethodAdapter.OnItemClickCallback{
            override fun onItemClicked(data: PaymentMethod) {
                selectPayment(data)
            }
        })

        rvMetodeBayar.layoutManager = LinearLayoutManager(activity)
        rvMetodeBayar.setHasFixedSize(true)
        rvMetodeBayar.adapter = adapter
    }

    private fun selectPayment(data: PaymentMethod) {
        if (data.isEnoughBalance!!)
        {
            Toast.makeText(activity, data.methodName, Toast.LENGTH_SHORT).show()
            (activity as TelkomPaymentActivity).setSelectedMethod(data)
            dismiss()
        }
        else  Toast.makeText(activity, "Balance is not enough. Please Top Up!", Toast.LENGTH_SHORT).show()
    }

    override fun onDestroy() {
        super.onDestroy()
    }
}
