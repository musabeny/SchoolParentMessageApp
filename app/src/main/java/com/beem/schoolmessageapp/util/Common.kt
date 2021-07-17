package com.beem.schoolmessageapp.util

import android.R
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.Spinner
import androidx.annotation.RequiresApi
import androidx.core.widget.doOnTextChanged
import com.beem.schoolmessageapp.adapter.DropdownAdapter
import com.beem.schoolmessageapp.database.Database
import com.beem.schoolmessageapp.model.*
import com.example.ewula.adapter.MessageAdapter
import com.example.ewula.adapter.OptionAdapter
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import java.util.*
import java.util.Base64.getEncoder
import kotlin.collections.ArrayList
import kotlin.collections.HashMap


class Common {

    lateinit var adapter:DropdownAdapter

   fun addOptionsToDb(database: Database){
       val addOptions = ArrayList<Options>()
       addOptions.add(Options(0,"School","You will send message to all parents"))
       addOptions.add(Options(0,"Class","You will send message to all parents of the specified class"))
       addOptions.add(Options(0,"Stream","You will send message to all parents of the specified Stream"))
       addOptions.add(Options(0,"Induvidual Student","You will send message to all parents of specified student"))

       database.insertOptions(addOptions)


   }

    fun addClassesToDb(database: Database){
        val addClasses = ArrayList<Options>()
        addClasses.add(Options(0,"Grade One","You will send message to all Grade One"))
        addClasses.add(Options(0,"Grade Two","You will send message to all Grade Two"))
        addClasses.add(Options(0,"Grade Three","You will send message to all Grade Three"))
        addClasses.add(Options(0,"Grade Four","You will send message to all Grade Four"))
        addClasses.add(Options(0,"Grade Five","You will send message to all Grade Five"))
        addClasses.add(Options(0,"Grade Six","You will send message to all Grade Six"))
        addClasses.add(Options(0,"Grade Seven","You will send message to all Grade Seven"))

        database.insertClass(addClasses)


    }

    fun addStreamToDb(database: Database){
        val addStream = ArrayList<Options>()
        addStream.add(Options(0,"Stream A","You will send message to all Stream A parents"))
        addStream.add(Options(0,"Stream B","You will send message to all Stream B parents"))
        addStream.add(Options(0,"Stream C","You will send message to all Stream C parents"))


        database.insertStream(addStream)


    }

    fun refleshList(lists:List<Options>,optionAdapter: OptionAdapter){
        optionAdapter.submitListOfOptions(lists)
        optionAdapter.notifyDataSetChanged()
    }

    fun refleshMessage(lists:List<MessageDetail>,messageAdapter: MessageAdapter){
        messageAdapter.submitListOfMessages(lists)
        messageAdapter.notifyDataSetChanged()
    }

    fun nextScreen(context: Activity, activity: Class<*>, choice:Int,selected:Int,group:Int){

        val intent = Intent(context, activity)
        intent.putExtra(nextScreenData,choice)
        intent.putExtra(selectedGroup,selected)
        intent.putExtra(groupChoice,group)
        context.startActivity(intent)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun convertBase64() : String{
        var apiKey = "6e301f4ca049df72"
        var secretKey = "YTVmZDkwNWMyYjM4MzZmYjg3ZjliZjE0MzJmMDcyYjQwOGRlNDM5OTEzY2UwZTVjZWZjMWIzNTU1MDcwNzk3NQ=="

        var finalKey = apiKey +":"+ secretKey



//        val encodedString =
//            Base64.getEncoder().withoutPadding().encodeToString(finalKey.toByteArray())
        val encodedString: String = Base64.getEncoder().encodeToString(finalKey.toByteArray())

//        val encodedString: String = Base64.encodeToString(finalKey.toByteArray(),0)
        Log.d(TAG,"Basic "+encodedString)
      return "Basic "+encodedString
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun getMap() : HashMap<String,String>{
        var hashMap = HashMap<String,String>()
        hashMap.put("Content-Type","application/json")
        hashMap.put("Authorization",convertBase64())

        Log.d(TAG," header "+hashMap)

        return  hashMap
    }

    fun checkValidation(textLayout: TextInputLayout, textInput: TextInputEditText, firstError:String, secondError:String,length:Int) {

        if(textInput.text!!.isEmpty()){
            textLayout.error = firstError
            textInput.doOnTextChanged { text, start, before, count ->
                if(length == 9){
                    if(text!!.length != length){
                        textLayout.error = secondError
                    }else{
                        textLayout.error = null
                    }

                }else{
                    if(text!!.length < length){
                        textLayout.error = secondError
                    }else{
                        textLayout.error = null
                    }
                }

            }
            return
        }else{
            textLayout.error = null
        }

    }


    fun dropDownMenuDb(spinner: Spinner, selection: ArrayList<Options>, context: Context,type:Int) {

        when(type){
             1 -> {
                Log.d(TAG," it is gender list")
                selection.add(0,Options(0,"SELECT CLASS",""))

                adapter = DropdownAdapter(
                    context,
                    R.layout.simple_spinner_item,
                    selection as java.util.ArrayList<Options>
                )

                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                spinner.adapter = adapter

                selectItem(spinner,adapter,1)
            }
             2 -> {
                selection.add(0,Options(0,"SELECT STREAM",""))
                 adapter = DropdownAdapter(
                    context,
                    android.R.layout.simple_spinner_item,
                    selection as java.util.ArrayList<Options>
                )

                 adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                spinner.adapter = adapter

                selectItem(spinner,adapter,2)
            }


        }


    }

    fun selectItem(spinner: Spinner,adapter: DropdownAdapter,type:Int ){
        Log.d("namana", " run this function")
        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                when(type){
                     1 ->{
                        Log.d(TAG," adapter is gender")
                        var options:Options = adapter.getItem(position) as Options
                        Datas.allClasses = options
                    }
                     2 ->{
                        Log.d(TAG," adapter is role")
                        var options:Options = adapter.getItem(position) as Options
                        Datas.allStream = options
                    }


                }

            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                Log.d("namana", " no item selected ")
            }


        }
    }

     fun getJsonReceiver(receiients:List<Recipient>,params: MutableMap<String?, Any>): JSONObject? {

        val jsonArray = JSONArray()
        val receiver = JSONObject()

        for(receipient in receiients){
            val jsonObject = JSONObject()
            jsonObject.put("recipient_id", receipient.recipient_id)
            jsonObject.put("dest_addr", receipient.dest_addr)
            jsonArray.put(jsonObject)
        }
        params["recipients"] = jsonArray
        receiver.put("recipients", jsonArray)
        Log.d(TAG," receiver json "+receiver)
        return receiver
    }


}