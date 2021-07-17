package com.example.ewula.network

import android.content.Context
import android.os.Build
import android.util.Log
import android.widget.EditText
import androidx.annotation.RequiresApi
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.StringRequest
import com.beem.schoolmessageapp.database.Database
import com.beem.schoolmessageapp.model.MessageDetail
import com.beem.schoolmessageapp.model.MessagePost
import com.beem.schoolmessageapp.model.TAG
import com.beem.schoolmessageapp.network.RemoteServer
import com.beem.schoolmessageapp.util.Common
import com.beem.schoolmessageapp.util.Datas
import com.example.ewula.adapter.MessageAdapter
import com.example.qms.loading.LoadingDialog
import org.json.JSONException
import org.json.JSONObject
import java.util.*


class NetworkApi {

    fun sendMessage(
        messagePost: MessagePost,
        loadingDialog: LoadingDialog,
        context: Context,
        common: Common,
        database: Database,
        messageAdapter: MessageAdapter,
        sendMessageBox: EditText
    ) {
        loadingDialog.load("Please wait", context).show()
        val url = Datas.BASE_URL+"send"
        val params: MutableMap<String?, Any> =
            HashMap()

        params["source_addr"] = messagePost.source_addr
        params["schedule_time"] = messagePost.schedule_time
        params["encoding"] = messagePost.encoding
        params["message"] = messagePost.message
         common.getJsonReceiver(messagePost.recipients,params)

        val parameer = JSONObject(params as Map<*, *>)

        val requests: JsonObjectRequest = object : JsonObjectRequest(
            Method.POST,
            url,
            parameer,
            Response.Listener { response ->
                loadingDialog.closeDialog()

                try{

                   val success = response.getBoolean("successful")
                    val messageSuccess = response.getString("message")
                    val requestId = response.getInt("request_id")
                    val code = response.getInt("code")
                    val valid = response.getInt("valid")
                    val invalid = response.getInt("invalid")
                    val duplicates = response.getInt("duplicates")

                    if(success){
                        loadingDialog.success("Valid recepient is "+valid+" , Invalid is "+invalid+" and Duplicates is "+duplicates,context,messageSuccess+" with code "+code)
                        val senderIds = Datas.checkSender.optionName


                        if(senderIds.equals("School",true)){
                            database.insertMessage(MessageDetail(0,messagePost.message,"","",senderIds,""))
                        }else if(senderIds.equals("Class",true)){
                            database.insertMessage(MessageDetail(0,messagePost.message,Datas.allClasses.optionName,"","",""))
                        }else if(senderIds.equals("Stream",true)){
                            database.insertMessage(MessageDetail(0,messagePost.message,Datas.allClasses.optionName,Datas.allStream.optionName,"",""))
                        }else if(senderIds.equals("Induvidual Student",true)){
                            database.insertMessage(MessageDetail(0,messagePost.message,"","","",Datas.selectedStudent.firstName+" "+Datas.selectedStudent.lastName))
                        }

                    }
                    sendMessageBox.text.clear()
                    common.refleshMessage(database.selectListOfMessageDetail(),messageAdapter)

                    Log.d("namana","result  "+response)
                }catch (e: JSONException){
                    Log.d("namana","error json ")
                    loadingDialog.closeDialog()
                    loadingDialog.errors("",context,"Something went wrong")

                }


            },
            Response.ErrorListener { error ->

                loadingDialog.closeDialog()
                loadingDialog.errors("",context,"Network Problem")
//                Log.d("namana"," Error Decription "+ String(error.networkResponse.data,Charsets.UTF_8))

//                Log.d("namana"," Error code "+error.networkResponse.statusCode)
//                Log.d("namana","message Error "+error.networkResponse.data)
            }){

            @RequiresApi(Build.VERSION_CODES.O)
            override fun getHeaders(): MutableMap<String, String> {
                val params: MutableMap<String, String> =
                    HashMap()

                params["Content-Type"] = "application/json"
                params["Authorization"] = common.convertBase64()
                Log.d(TAG,"inside security "+params)
                return params
            }





        }
        RemoteServer.getmInstance(context).addToRequetQue(requests)
    }







    //testing
    fun checkBalance(context: Context,loadingDialog: LoadingDialog,common: Common) {

        val url = "https://apisms.beem.africa/public/v1/vendors/balance"
        loadingDialog.load("Please wait", context).show()

        val params: MutableMap<String?, String?> =
            HashMap()

        val requests: StringRequest = object : StringRequest(
            Method.GET,
            url,
            Response.Listener { response ->
                loadingDialog.closeDialog()
//                val getBalance = JSONArray(response)

                val jsonObject = JSONObject(response)
                val data = jsonObject.getJSONObject("data")
                val credit_balance = data.getInt("credit_balance")
                loadingDialog.success("",context,"Your balance is "+credit_balance)

            },
            Response.ErrorListener { error ->
                loadingDialog.closeDialog()
                loadingDialog.errors("",context,"Network Problem")
            }){

            @RequiresApi(Build.VERSION_CODES.O)
            override fun getHeaders(): MutableMap<String, String> {
                val params: MutableMap<String, String> =
                    HashMap()
                params["Content-Type"] = "application/json"
                params["Authorization"] = common.convertBase64()
                return params
            }


        }
        RemoteServer.getmInstance(context).addToRequetQue(requests)

    }



}