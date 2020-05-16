package com.rifqimukhtar.phonepayment.activities

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.WindowManager
import android.widget.Toast
import androidx.constraintlayout.solver.GoalRow
import androidx.lifecycle.Observer
import com.rifqimukhtar.phonepayment.R
import com.rifqimukhtar.phonepayment.db.entity.BaseUser
import com.rifqimukhtar.phonepayment.db.entity.SendUser
import com.rifqimukhtar.phonepayment.db.entity.User
import com.rifqimukhtar.phonepayment.rest.ApiClient
import com.rifqimukhtar.phonepayment.rest.ApiInteface
import com.rifqimukhtar.phonepayment.viewmodel.UserViewModel
import kotlinx.android.synthetic.main.activity_main_menu.*
import kotlinx.android.synthetic.main.activity_register_verification.*
import org.koin.android.ext.android.inject
import org.koin.android.ext.android.startKoin
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainMenuActivity : AppCompatActivity() {
    companion object val API_KEY = "xxxxxx"
    val currentUserID = 1
    var user:User? = null
    private val userViewModel: UserViewModel by inject()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_menu)
        activateLoading()
        buttonGroup()
        getUser2()
    }

//    private fun getUser() {
//        loadingMainMenu.show()
//        frameTransparent.visibility = VISIBLE
//        val sendUser = SendUser(1)
//        val apiCall = ApiClient.getClient()?.create(ApiInteface::class.java)
//        apiCall?.getUser(sendUser)
//            ?.enqueue(object : Callback<BaseUser> {
//            override fun onResponse(call: Call<BaseUser>, response: Response<BaseUser>) {
//                if (response.isSuccessful) {
//                    loadingMainMenu.hide()
//                    frameTransparent.visibility = GONE
//                    val item = response.body()!!.userProfile
//                    user = User(item?.idUser, item?.name, item?.email, item?.password, item?.phoneNumber, item?.balance, item?.token)
//
//                    Log.d("State", item.toString())
//                    setUserDetail(user!!)
//                }
//            }
//
//            override fun onFailure(call: Call<BaseUser>, t: Throwable) {
//                loadingMainMenu.hide()
//                frameTransparent.visibility = GONE
//                Toast.makeText(applicationContext, "Request Failed", Toast.LENGTH_SHORT).show()
//                Log.d("Failed", t.message)
//            }
//        })
//    }

    fun getUser2(){
        val preference = getSharedPreferences("Pref_Profile", 0)
        val userId = preference.getInt("PREF_USERID", 0)
        val sendUser = SendUser(userId)

        userViewModel.getUser(sendUser).observe(this, Observer<User> {
            user = it
            setUserDetail(it)
            deactivateLoading()
        })
    }

    fun activateLoading() {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
            WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
        loadingMainMenu.visibility = VISIBLE
        frameTransparent.visibility = VISIBLE
    }

    fun deactivateLoading() {
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
        loadingMainMenu.visibility = GONE
        frameTransparent.visibility = GONE
    }
    private fun setUserDetail(user: User) {
        tvWelcomeUser.text = "Welcome, ${user.name}"
        tvUserBalance.text = "Rp ${user.balance}"

        val preference = getSharedPreferences("Pref_Profile2", 0)
        val emailUser = user.email
        val editor = preference.edit()
        editor.putString("PREF_EMAIL", emailUser)
        editor.apply()
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
