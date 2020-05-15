package com.rifqimukhtar.phonepayment.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.rifqimukhtar.phonepayment.R
import com.rifqimukhtar.phonepayment.db.di.repositoryModule
import com.rifqimukhtar.phonepayment.db.di.uiModule
import org.koin.android.ext.android.startKoin

class SplashActivity : AppCompatActivity() {

    companion object{
        private const val DURATION = 3000L
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
//        startKoin(this,
//            listOf(
//                repositoryModule,
//                uiModule
//            )
//        )
        Handler().postDelayed({
            val preference = getSharedPreferences("Pref_Profile",0)
            val isLogin = preference.getBoolean("PREF_ISLOGIN", false)
            if (!isLogin){
                startActivity(Intent(this@SplashActivity, LoginActivity::class.java))
                finish()
            } else {
                startActivity(Intent(this@SplashActivity, MainMenuActivity::class.java))
                finish()
            }
        }, DURATION)
    }
}
