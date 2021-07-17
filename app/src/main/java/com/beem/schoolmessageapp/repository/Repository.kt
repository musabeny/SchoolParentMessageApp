package com.beem.schoolmessageapp.repository

import android.os.Build
import androidx.annotation.RequiresApi
import com.beem.schoolmessageapp.api.RetrofitInstance
import com.beem.schoolmessageapp.model.Balance
import com.beem.schoolmessageapp.model.MessageDetail
import com.beem.schoolmessageapp.model.MessagePost
import com.beem.schoolmessageapp.util.Common
import retrofit2.Response

class Repository {
    var common = Common()


    @RequiresApi(Build.VERSION_CODES.O)
    suspend fun getBalance() : Response<Balance> {
        return RetrofitInstance.api.getBalance(common.getMap())
    }

    @RequiresApi(Build.VERSION_CODES.O)
    suspend fun pushMessage(messagePost: MessagePost):Response<MessagePost>{
        return RetrofitInstance.api.pushMessage(messagePost)
    }
}