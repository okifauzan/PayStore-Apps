package com.rifqimukhtar.phonepayment.fragments

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager

import com.rifqimukhtar.phonepayment.R
import com.rifqimukhtar.phonepayment.activities.HistoryActivity
import com.rifqimukhtar.phonepayment.activities.MainMenuActivity
import com.rifqimukhtar.phonepayment.activities.TelkomPaymentActivity
import com.rifqimukhtar.phonepayment.adapters.HistoryAdapter
import com.rifqimukhtar.phonepayment.db.entity.BaseResponse
import com.rifqimukhtar.phonepayment.db.entity.BillHistory
import com.rifqimukhtar.phonepayment.db.entity.PaymentMethod
import com.rifqimukhtar.phonepayment.db.entity.SendUser
import com.rifqimukhtar.phonepayment.rest.ApiClient
import com.rifqimukhtar.phonepayment.rest.ApiInteface
import kotlinx.android.synthetic.main.fragment_list_history.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*
import kotlin.collections.ArrayList

/**
 * A simple [Fragment] subclass.
 */
class ListHistoryFragment : Fragment() {
    var listBillHistory: ArrayList<BillHistory> = ArrayList()
    private lateinit var adapter: HistoryAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_list_history, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter = HistoryAdapter()
        adapter.notifyDataSetChanged()

        generateBillHistory()
        buttonGroup()
    }


    private fun buttonGroup() {
        ibBackFromHistory.setOnClickListener {
            startActivity(Intent(activity, MainMenuActivity::class.java))
        }
    }

    private fun generateBillHistory() {
        val preference = activity!!.getSharedPreferences("Pref_Profile", 0)
        val userId = preference.getInt("PREF_USERID", 0)
        val sendUser = SendUser(userId)
        val apiCall = ApiClient.getClient()?.create(ApiInteface::class.java)
        apiCall?.getHistory(sendUser)?.enqueue(object : Callback<BaseResponse<List<BillHistory>>>{
            override fun onResponse(call: Call<BaseResponse<List<BillHistory>>>, response: Response<BaseResponse<List<BillHistory>>>) {
                if (response.isSuccessful){
                    val temp = response.body()!!.data
                    temp!!.forEach{
                        val billHistory = BillHistory(it.idPayment, it.idUser, it.name, it.phoneNumber, it.balance,
                            it.idBill, it.telephoneNumber, it.status, it.amount, it.idPaymentMethod, it.method, it.timestamp)
                        listBillHistory.add(billHistory)
                        Log.d("Objek", "name -> ${it.name}")
                    }
                    setupRecyclerView()
                    Log.d("listBill", listBillHistory.toString())
                } else {
                    Log.d("gagal", listBillHistory.toString())
                }
            }

            override fun onFailure(call: Call<BaseResponse<List<BillHistory>>>, t: Throwable) {
                Toast.makeText(context, "Can't Load History", Toast.LENGTH_SHORT).show()
                Log.d("Failed", "Can't Load History")
            }
        })

        /*for (i in 0..15)
        {
            val bill = BillHistory(1, 1, "Aldie", "811234567", 200000,
                1, "0227254321", 50000, 1, "bank",
                "2020-05-14 14:40:33" )
            listBillHistory.add(bill)
        }*/
    }

    private fun setupRecyclerView() {

        adapter.setItem(listBillHistory)
        adapter.setOnItemClickCallback(object : HistoryAdapter.OnItemClickCallback{
            override fun onItemClicked(data: BillHistory) {
                selectBill(data)
            }
        })

        rvHistory.layoutManager = LinearLayoutManager(activity)
        rvHistory.setHasFixedSize(true)
        rvHistory.adapter = adapter
    }

    private fun selectBill(data: BillHistory) {
        Toast.makeText(activity, data.amount.toString(), Toast.LENGTH_SHORT).show()
        (activity as HistoryActivity).showDetailHistoryFragment(data)
    }
}
