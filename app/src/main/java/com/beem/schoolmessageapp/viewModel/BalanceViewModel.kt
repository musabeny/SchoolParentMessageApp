package com.beem.schoolmessageapp.viewModel

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.beem.schoolmessageapp.model.Balance
import com.beem.schoolmessageapp.model.MessageDetail
import com.beem.schoolmessageapp.model.MessagePost
import com.beem.schoolmessageapp.repository.Repository
import kotlinx.coroutines.launch
import retrofit2.Response

class BalanceViewModel(private val repository: Repository) : ViewModel() {
   val myResponse:MutableLiveData<Response<Balance>> = MutableLiveData()
    val messageResponse:MutableLiveData<Response<MessagePost>> = MutableLiveData()
    @RequiresApi(Build.VERSION_CODES.O)
    fun  getBalance(){
        
      viewModelScope.launch {
         val response = repository.getBalance()
          myResponse.value = response
      }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun pushMessage(messagePost: MessagePost){
        viewModelScope.launch {
            val response = repository.pushMessage(messagePost)
            messageResponse.value = response
        }
    }
}