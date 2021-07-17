package com.beem.schoolmessageapp.util

import com.beem.schoolmessageapp.model.Options
import com.beem.schoolmessageapp.model.Student

class Datas {
    companion object{
        lateinit var option: Options

        lateinit var allSchool: Options
        lateinit var allClasses: Options
        lateinit var allStream: Options
        lateinit var checkSender:Options
        lateinit var selectedStudent: Student
         const val BASE_URL = "https://apisms.beem.africa/v1/"
        var hintText = ""
        var classes = ""
        var stream = 0
    }
}