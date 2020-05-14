package com.rifqimukhtar.phonepayment.activities

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.rifqimukhtar.phonepayment.R
import com.rifqimukhtar.phonepayment.db.entity.PaymentMethod
import com.rifqimukhtar.phonepayment.fragments.PaymentMethodFragment
import com.rifqimukhtar.phonepayment.fragments.PaymentResultFragment
import kotlinx.android.synthetic.main.activity_telkom_payment.*
import kotlinx.android.synthetic.main.fragment_detail_bill.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [DetailBillFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class DetailBillFragment : DialogFragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private var isEnoughBalance : Boolean? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val flags = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_FULLSCREEN
        activity!!.window.decorView.systemUiVisibility = flags
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
            linearLayoutDetailTagihan.visibility = View.GONE
            btnCekTagihan.visibility = View.VISIBLE

            // TODO("call bayar tagihan api")
            showSuccessDialog()
        }
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment DetailBillFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            DetailBillFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
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
