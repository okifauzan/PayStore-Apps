package com.rifqimukhtar.phonepayment.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.Toast
import com.rifqimukhtar.phonepayment.R
import com.rifqimukhtar.phonepayment.db.entity.BaseUser
import com.rifqimukhtar.phonepayment.db.entity.SendUser
import com.rifqimukhtar.phonepayment.db.entity.User
import com.rifqimukhtar.phonepayment.rest.ApiClient
import com.rifqimukhtar.phonepayment.rest.ApiInteface
import kotlinx.android.synthetic.main.activity_main_menu.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainMenuActivity : AppCompatActivity() {
    companion object val API_KEY = "xxxxxx"
    val currentUserID = 1
    var user:User? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_menu)
        buttonGroup()
        getUser()
    }

    private fun getUser() {
        loadingMainMenu.show()
        frameTransparent.visibility = VISIBLE
        val sendUser = SendUser(1)
        val apiCall = ApiClient.getClient(API_KEY, applicationContext)?.create(ApiInteface::class.java)
        apiCall?.getUser(sendUser)
            ?.enqueue(object : Callback<BaseUser> {
            override fun onResponse(call: Call<BaseUser>, response: Response<BaseUser>) {
                if (response.isSuccessful) {
                    loadingMainMenu.hide()
                    frameTransparent.visibility = GONE
                    val item = response.body()!!.userProfile
                    user = User(item?.idUser, item?.name, item?.email, item?.password, item?.phoneNumber, item?.balance, item?.token)

                    Log.d("State", item.toString())
                    setUserDetail(user!!)
                }
            }

            override fun onFailure(call: Call<BaseUser>, t: Throwable) {
                loadingMainMenu.hide()
                frameTransparent.visibility = GONE
                Toast.makeText(applicationContext, "Request Failed", Toast.LENGTH_SHORT).show()
                Log.d("Failed", t.message)
            }
        })
    }

    private fun setUserDetail(user: User) {
        tvWelcomeUser.text = "Weclome, ${user.name}"
        tvUserBalance.text = "Rp ${user.balance}"
    }

    private fun buttonGroup() {
        btnHistory.setOnClickListener {
            startActivity(Intent(this, HistoryActivity::class.java))
        }

        btnTelkomOption.setOnClickListener {
            startActivity(Intent(this, TelkomPaymentActivity::class.java))
        }
        cvUserProfile.setOnClickListener {

            val bundle = Bundle()
            bundle.putSerializable("currentUser", user)

            val intent = Intent(this, ProfileActivity::class.java)
            intent.putExtras(bundle)
            startActivity(intent)
        }
    }

}
