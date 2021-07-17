package com.beem.schoolmessageapp.api

import com.beem.schoolmessageapp.model.Balance
import com.beem.schoolmessageapp.model.MessageDetail
import com.beem.schoolmessageapp.model.MessagePost
import retrofit2.Response
import retrofit2.http.*

interface BalanceApi {

//    @Headers("Authorization: Basic bXVzYWJlbmkxNEBnbWFpbC5jb21rYXphbW95bw-")
    @GET("vendors/balance")
    suspend fun getBalance(@HeaderMap header:Map<String,String> ) : Response<Balance>

    @POST("send")
    suspend fun pushMessage(@Body messagePost: MessagePost) : Response<MessagePost>
}