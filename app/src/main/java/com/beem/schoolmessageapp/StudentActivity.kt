package com.beem.schoolmessageapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.core.widget.doOnTextChanged
import com.beem.schoolmessageapp.database.Database
import com.beem.schoolmessageapp.model.Options
import com.beem.schoolmessageapp.model.Student
import com.beem.schoolmessageapp.model.TAG
import com.beem.schoolmessageapp.util.Common
import com.beem.schoolmessageapp.util.Datas
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import kotlinx.android.synthetic.main.activity_stream.*
import kotlinx.android.synthetic.main.activity_student.*

class StudentActivity : AppCompatActivity() {
    lateinit var common: Common
    lateinit var database: Database
    lateinit var listOfClassess : List<Options>
    lateinit var listOfStream : List<Options>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_student)
        initView()
    }

    fun initView(){

        val toolbar = studentTools.findViewById<Toolbar>(R.id.toolbares)
        toolbar.setTitle("Add Student")
        setSupportActionBar(toolbar)
        toolbar.setNavigationIcon(R.drawable.back_btn)
        toolbar.setNavigationOnClickListener(View.OnClickListener {
            finish()
        })


        common = Common()
        database = Database(this)

        listOfClassess = database.selectListOfClass()
        listOfStream = database.selectListOfStream()

        common.dropDownMenuDb(selectClass, listOfClassess as ArrayList<Options>,this,1)
        common.dropDownMenuDb(selectStream, listOfStream as ArrayList<Options>,this,2)

        Log.d(TAG," students "+database.selectListOfStudent())
    }


    fun saveStudent(view: View){

        common.checkValidation(firstName,firstNameEdit,"Firstname required","Firstname must contain three character or more",3)
        common.checkValidation(lastName,lastNameEdit,"Lastname required","Lastname must contain three character or more",3)
        common.checkValidation(phoneNumber,phoneNumberEdit,"Phonenumber required","Phonenumber must contain nine character ",9)


        val firstnames = firstNameEdit.text.toString()

        val lastname = lastNameEdit.text.toString()
        val phonenumber = "255"+phoneNumberEdit.text.toString()
        val classId = Datas.allClasses.id
        val streamId = Datas.allStream.id


        if(firstnames.length <3){
            Toast.makeText(this,"firstnames must contain three or more character",Toast.LENGTH_SHORT).show()
            Log.d(TAG," firstnames "+firstnames)
            return
        }

        if(lastname.length <3){
            Toast.makeText(this,"lastname must contain three or more character",Toast.LENGTH_SHORT).show()
            Log.d(TAG," lastname "+lastname)
            return
        }

        if(phonenumber.length < 12 || phonenumber.length > 12 ){
            Toast.makeText(this,"phonenumber must contain twelve  character",Toast.LENGTH_SHORT).show()
            Log.d(TAG," phone "+phonenumber)
            return
        }



        if(Datas.allClasses.id == 0){
            Toast.makeText(this,"Class not selected",Toast.LENGTH_SHORT).show()
            return
        }

        if(Datas.allStream.id == 0){
            Toast.makeText(this,"Stream not selected",Toast.LENGTH_SHORT).show()
            return
        }

        val result = database.insertStudent(Student(0,firstnames,lastname,phonenumber,classId,streamId))
        Log.d(TAG," result "+result)
        if(result.toInt() >= 1){
            finish()
        }
    }




}