package com.rifqimukhtar.phonepayment.fragments

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.Toast
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.Observer

import com.rifqimukhtar.phonepayment.R
import com.rifqimukhtar.phonepayment.activities.MainMenuActivity
import com.rifqimukhtar.phonepayment.activities.TelkomPaymentActivity
import com.rifqimukhtar.phonepayment.db.entity.*
import com.rifqimukhtar.phonepayment.viewmodel.BillViewModel
import com.rifqimukhtar.phonepayment.viewmodel.UserViewModel
import kotlinx.android.synthetic.main.fragment_insert_number.*
import kotlinx.android.synthetic.main.fragment_insert_number.frameTransparent
import kotlinx.android.synthetic.main.fragment_insert_number.loadingMainMenu
import org.koin.android.ext.android.inject

/**
 * A simple [Fragment] subclass.
 */
class InsertNumberFragment : Fragment() {

    private val billViewModel: BillViewModel by inject()
    private val userViewModel: UserViewModel by inject()
    companion object val API_KEY = "xxxxxx"
    val patternHandphone = "^[0-9]{9,12}\$".toRegex()
    private var isEnoughBalance = true
    private var selectedMethod:PaymentMethod? = null
    private var currentUser:User? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_insert_number, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        currentUser?.balance = 0
        buttonGroup()
    }

    private fun buttonGroup() {
        btnCekTagihan.setOnClickListener{
            val inputPhoneNumber = etNomorTelepon.text.toString()
            if (inputPhoneNumber.trim().isEmpty())
            {
                Toast.makeText(activity, "Phone number cannot be empty!", Toast.LENGTH_SHORT).show()
            } else
            {
                activateLoading()
                val preference = activity!!.getSharedPreferences("Pref_Profile", 0)
                val userId = preference.getInt("PREF_USERID", 0)
                getCurrentUser(SendUser(userId), inputPhoneNumber)

            }
        }

        ibBackFromTagihan.setOnClickListener {
            startActivity(Intent(activity, MainMenuActivity::class.java))
        }
    }

    private fun getCurrentUser(userID: SendUser, inputPhoneNumber:String) {
        userViewModel.getUser(userID).observe(activity as TelkomPaymentActivity, Observer<User> {
            currentUser = it
            Log.d("State", "get current user ${currentUser!!.name}")
            getPhoneBill(inputPhoneNumber)
        })
    }

    private fun getPhoneBill(inputPhoneNumber: String) {

//        //TODO("request API get user balance & get phone bill")
        val phoneNumeber = SendPhone(inputPhoneNumber)
        billViewModel.getPaymentDetail(phoneNumeber).observe(activity as TelkomPaymentActivity, Observer<PhoneBill>{
            if (it.status=="unpaid")
            {
                val item = it
                val bill = PhoneBill(item?.idBill, item?.telephoneOwner, item?.telephoneNumber, item?.month,
                    item?.amount, item?.status)
                Log.d("State", "bill viewmodel ${it.status}")
                checkWalletBalance(bill)
                if(selectedMethod!=null){
                    deactivateLoading()
                    (activity as TelkomPaymentActivity).showDetailBillFragment(bill, selectedMethod!!, currentUser?.balance!!)
                    Log.d("State", selectedMethod.toString())
                }
            } else{
                showNotFoundDialog()
                Toast.makeText(activity, "Cant found unpaid bill", Toast.LENGTH_SHORT).show()
                deactivateLoading()
            }
        })
    }


    private fun checkWalletBalance(phoneBill: PhoneBill) {
        //TODO("get real balance value from user")
        //val balance = 50000
        val balance = currentUser?.balance!!

        if (balance <= phoneBill.amount!!)
        {
            //Not enough balance, use Virtual Acc
            //TODO("add real user virtual number")
            val virtualNumber = "8001${phoneBill.telephoneNumber}"
            val virtualAcc = PaymentMethod(R.drawable.ic_virtual_acc, "Virtual Account",virtualNumber, false,2)
            setActivitySelectedMethod(virtualAcc)

            //setvalue for send in showPaymentMethod
            isEnoughBalance = false

        } else{
            //Enough balance, use wallet
            val methodValue = balance.toString()
            val eWallet = PaymentMethod(R.drawable.ic_wallet, "PayStore Wallet", methodValue, true, 1)
            setActivitySelectedMethod(eWallet)
            //setvalue for send in showPaymentMethod
            isEnoughBalance = true
        }
    }

    private fun setActivitySelectedMethod(method: PaymentMethod) {
        selectedMethod = method
        (activity as TelkomPaymentActivity).setSelectedMethod(method)
    }


    private fun showNotFoundDialog() {
        val dialogFragment = PaymentNotFound()
        var ft: FragmentTransaction = activity!!.supportFragmentManager.beginTransaction()
        ft?.addToBackStack(null)
        dialogFragment.show(ft!!, "dialog")
    }
    fun activateLoading() {
        activity!!.getWindow().setFlags(
            WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
            WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
        loadingMainMenu.visibility = View.VISIBLE
        frameTransparent.visibility = View.VISIBLE
    }

    fun deactivateLoading() {
        activity!!.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
        loadingMainMenu.visibility = View.GONE
        frameTransparent.visibility = View.GONE
    }
}
