package com.rifqimukhtar.phonepayment.fragments

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.DialogFragment

import com.rifqimukhtar.phonepayment.R
import kotlinx.android.synthetic.main.fragment_payment_not_found.*
import kotlinx.android.synthetic.main.fragment_payment_result.*

/**
 * A simple [Fragment] subclass.
 */
class PaymentNotFound : DialogFragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        dialog!!.window!!.setGravity(Gravity.FILL_HORIZONTAL or Gravity.BOTTOM)
        val p = dialog!!.window!!.attributes
        p.width = ViewGroup.LayoutParams.MATCH_PARENT
        p.softInputMode = WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE
        return inflater.inflate(R.layout.fragment_payment_not_found, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        buttonGroup()
    }

    private fun buttonGroup() {
        btnCloseNotFound.setOnClickListener{
            dismiss()
        }
        btnIconCloseNotFound.setOnClickListener {
            dismiss()
        }
    }
    override fun onDestroy() {
        super.onDestroy()
    }
}
