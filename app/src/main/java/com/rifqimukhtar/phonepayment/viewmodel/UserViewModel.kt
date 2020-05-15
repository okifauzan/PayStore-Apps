package com.rifqimukhtar.phonepayment.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.rifqimukhtar.phonepayment.db.entity.SendUser
import com.rifqimukhtar.phonepayment.db.entity.User
import com.rifqimukhtar.phonepayment.repository.UserRepository

class UserViewModel(private var userRepository: UserRepository):ViewModel(){
    fun getUser(sendUser: SendUser): LiveData<User>{
        return userRepository.getUser(sendUser)
    }
}