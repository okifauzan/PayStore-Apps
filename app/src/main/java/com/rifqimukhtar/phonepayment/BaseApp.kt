package com.rifqimukhtar.phonepayment

import android.app.Application
import com.rifqimukhtar.phonepayment.db.di.repositoryModule
import com.rifqimukhtar.phonepayment.db.di.uiModule
import org.koin.android.ext.android.startKoin

class BaseApp: Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin(this, listOf(
            repositoryModule,
            uiModule
        ))
    }
}