package com.rifqimukhtar.phonepayment.db.di

import com.rifqimukhtar.phonepayment.repository.BillRepository
import com.rifqimukhtar.phonepayment.repository.UserRepository
import com.rifqimukhtar.phonepayment.rest.ApiClient
import com.rifqimukhtar.phonepayment.viewmodel.BillViewModel
import com.rifqimukhtar.phonepayment.viewmodel.UserViewModel
import org.koin.android.viewmodel.ext.koin.viewModel
import org.koin.dsl.module.module

val repositoryModule = module {
    single { UserRepository(get()) }
    single { BillRepository(get()) }
}
val uiModule = module {
    viewModel{ UserViewModel(get())}
    viewModel{ BillViewModel(get())}

   //factory { HistoryAdapter() }
   // viewModel { HistoryViewModel(get()) }
}