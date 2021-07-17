package com.beem.schoolmessageapp.database

import android.content.ContentValues
import android.content.Context
import android.database.DatabaseUtils
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import com.beem.schoolmessageapp.model.MessageDetail
import com.beem.schoolmessageapp.model.Options
import com.beem.schoolmessageapp.model.Student
import com.beem.schoolmessageapp.model.TAG
import com.beem.schoolmessageapp.util.Datas


val DATABASENAME = "SchoolMessage"
val TABLENAME ="Options"
val ID = "id"
val OPTNAME = "OptionName"
val OPTDESCR = "OptionDescription"

val TABLENAMECLASS ="Classes"
val IDCLASS = "id"
val CLASSNAME = "ClassName"
val CLASSDESCR = "OptionDescription"

val TABLENAMESTREAM ="Stream"
val IDSTREAM = "id"
val STREAMNAME = "ClassName"
val STREAMDESCR = "OptionDescription"

val TABLENAMESTUDENT ="Student"
val IDSSTUDENT = "id"
val STUDENTFIRSTNAME = "FIrstName"
val STUDENTLASTNAME = "LastName"
val STUDENTPHONENUMBER = "PhoneNumber"
val STUDENTCLASS = "ClassId"
val STUDENTSTREAM= "StreamId"

val TABLENAMEMESSAGE ="Messages"
val IDMESSAGE= "id"
val MESSAGEBODY = "Message"
val MESSAGECLASS = "ClassIds"
val MESSAGESTREAM = "StreamIds"
val MESSAGEALLSCHOOL = "SchoolIds"
val MESSAGEINDIVIDUAL= "Individual"

lateinit var db: SQLiteDatabase
class Database(context:Context) : SQLiteOpenHelper(context,DATABASENAME,null,1) {
    override fun onCreate(p0: SQLiteDatabase?) {
        val createTableOption = "CREATE TABLE "+TABLENAME+"( "+ ID+" integer primary key  autoincrement , "+
                OPTNAME+" text,"+
                OPTDESCR +" text )"

        val createTableCLass = "CREATE TABLE "+TABLENAMECLASS+"( "+ IDCLASS+" integer primary key  autoincrement , "+
                CLASSNAME+" text,"+
                CLASSDESCR +" text )"

        val createTableStream = "CREATE TABLE "+ TABLENAMESTREAM+"( "+ IDSTREAM+" integer primary key  autoincrement , "+
                STREAMNAME+" text,"+
                STREAMDESCR +" text )"

        val createTableStudent = "CREATE TABLE "+ TABLENAMESTUDENT+"( "+ IDSSTUDENT+" integer primary key  autoincrement , "+
                STUDENTFIRSTNAME+" text,"+
                STUDENTLASTNAME +" text, " +
                STUDENTPHONENUMBER+" text, " +
                STUDENTCLASS+" integer, "+
                STUDENTSTREAM+" integer "+
                ")"

        val createTableMessage = "CREATE TABLE "+ TABLENAMEMESSAGE+"( "+ IDMESSAGE+" integer primary key  autoincrement , "+
                MESSAGEBODY+" text,"+
                MESSAGECLASS +" text, " +
                MESSAGESTREAM+" text, " +
                MESSAGEALLSCHOOL+" text, "+
                MESSAGEINDIVIDUAL+" text "+
                ")"


        p0?.execSQL(createTableOption)
        p0?.execSQL(createTableCLass)
        p0?.execSQL(createTableStream)
        p0?.execSQL(createTableStudent)
        p0?.execSQL(createTableMessage)

        if (p0 != null) {
            db = p0
        }else{
            Log.d(TAG,"database not conncetd")
        }
    }

    fun insertOptions( options: List<Options>)  {
        db = this.writableDatabase
        var cv  = ContentValues()
        for ( option in options ){
            cv.put(OPTNAME,option.optionName)
            cv.put(OPTDESCR,option.optionDescription)
            var result = db.insert(TABLENAME,null,cv)
            Log.d(TAG," value inserted "+result)
        }


    }

    fun selectListOfOptions() : ArrayList<Options> {
        db = this.writableDatabase
        var query = "SELECT * FROM "+ TABLENAME
        val cursor  = db.rawQuery(query,null)
        var listOfOptions = arrayListOf<Options>()
        if(cursor != null){
            if(cursor.count > 0){
                cursor.moveToFirst()
                do{
                    val user = Options(cursor.getInt(0),cursor.getString(1),cursor.getString(2))
                    listOfOptions.add(user)
                }while (cursor.moveToNext())
            }
        }
        return listOfOptions
    }

    fun countOptions() : Long {
        db = this.writableDatabase
        val count = DatabaseUtils.queryNumEntries(db,TABLENAME)

        return count
    }

    fun insertClass(classes: List<Options>){

        db = this.writableDatabase
        var cv  = ContentValues()
        for ( option in classes ){
            cv.put(CLASSNAME,option.optionName)
            cv.put(CLASSDESCR,option.optionDescription)
            var result = db.insert(TABLENAMECLASS,null,cv)
            Log.d(TAG," value inserted "+result)
        }

    }

    fun selectListOfClass() : ArrayList<Options> {
        db = this.writableDatabase
        val query = "SELECT * FROM "+ TABLENAMECLASS
        val cursor  = db.rawQuery(query,null)
        val listOfCLasses = arrayListOf<Options>()
        if(cursor != null){
            if(cursor.count > 0){
                cursor.moveToFirst()
                do{
                    val user = Options(cursor.getInt(0),cursor.getString(1),cursor.getString(2))
                    listOfCLasses.add(user)
                }while (cursor.moveToNext())
            }
        }
        return listOfCLasses
    }

    fun selectClass(id: Int) : Options? {
        db = this.writableDatabase
        var query = "SELECT * FROM "+ TABLENAMECLASS+" WHERE "+ IDCLASS+" = "+id
        val cursor  = db.rawQuery(query,null)
        var option: Options? = null
        if(cursor != null){
            if(cursor.count > 0){
                cursor.moveToFirst()
                do{

                    option =  Options(cursor.getInt(0),
                        cursor.getString(1),cursor.getString(2))

                }while (cursor.moveToNext())
            }
        }
        return option
    }

    fun countCLasses() : Long {
        db = this.writableDatabase
        val count = DatabaseUtils.queryNumEntries(db,TABLENAMECLASS)

        return count
    }

    fun insertStream(streams: List<Options>){

        db = this.writableDatabase
        var cv  = ContentValues()
        for ( stream in streams ){
            cv.put(STREAMNAME,stream.optionName)
            cv.put(STREAMDESCR,stream.optionDescription)
            var result = db.insert(TABLENAMESTREAM,null,cv)
            Log.d(TAG," value inserted "+result)
        }

    }

    fun selectListOfStream() : ArrayList<Options> {
        db = this.writableDatabase
        val query = "SELECT * FROM "+ TABLENAMESTREAM
        val cursor  = db.rawQuery(query,null)
        val listOfSTream = arrayListOf<Options>()
        if(cursor != null){
            if(cursor.count > 0){
                cursor.moveToFirst()
                do{
                    val stream = Options(cursor.getInt(0),cursor.getString(1),cursor.getString(2))
                    listOfSTream.add(stream)
                }while (cursor.moveToNext())
            }
        }
        return listOfSTream
    }

    fun countStream() : Long {
        db = this.writableDatabase
        val count = DatabaseUtils.queryNumEntries(db, TABLENAMESTREAM)

        return count
    }

    fun insertStudent( students: Student) : Long  {
        db = this.writableDatabase
        var cv  = ContentValues()

        cv.put(STUDENTFIRSTNAME,students.firstName)
        cv.put(STUDENTLASTNAME,students.lastName)
        cv.put(STUDENTPHONENUMBER,students.phoneNumber)
        cv.put(STUDENTCLASS,students.classess)
        cv.put(STUDENTSTREAM,students.stream)
            val result = db.insert(TABLENAMESTUDENT,null,cv)
            Log.d(TAG," value inserted "+result)

       return result

    }


    fun selectListOfStudent() : ArrayList<Student> {
        db = this.writableDatabase
        val query = "SELECT * FROM "+ TABLENAMESTUDENT
        val cursor  = db.rawQuery(query,null)
        val listOfStudent = arrayListOf<Student>()
        if(cursor != null){
            if(cursor.count > 0){
                cursor.moveToFirst()
                do{
                    val student = Student(cursor.getInt(0),cursor.getString(1),cursor.getString(2),cursor.getString(3),cursor.getInt(4),cursor.getInt(5))
                    listOfStudent.add(student)
                }while (cursor.moveToNext())
            }
        }
        return listOfStudent
    }

    fun selectedStudentForMessage(selectedOption:Int,groupChoice:Int) : ArrayList<Student> {
        db = this.writableDatabase
        var query = ""

        if(groupChoice == 2){
            Log.d(TAG,"class "+Datas.allClasses.id)
             query = "SELECT * FROM "+ TABLENAMESTUDENT+" WHERE "+ STUDENTCLASS+" = "+Datas.allClasses.id
        }else{
            query = "SELECT * FROM "+ TABLENAMESTUDENT+" WHERE "+ STUDENTSTREAM+" = "+ Datas.allStream.id+" AND "+STUDENTCLASS+" = "+Datas.allClasses.id
        }

        val cursor  = db.rawQuery(query,null)
        val listOfStudent = arrayListOf<Student>()
        if(cursor != null){
            if(cursor.count > 0){
                cursor.moveToFirst()
                do{
                    val student = Student(cursor.getInt(0),cursor.getString(1),cursor.getString(2),cursor.getString(3),cursor.getInt(4),cursor.getInt(5))
                    listOfStudent.add(student)
                }while (cursor.moveToNext())
            }
        }
        return listOfStudent
    }

    fun selectListOfPhoneNumber() : ArrayList<String> {
        db = this.writableDatabase
        val query = "SELECT "+ STUDENTPHONENUMBER+" FROM "+ TABLENAMESTUDENT
        val cursor  = db.rawQuery(query,null)
        val listOfphoneNumber = arrayListOf<String>()
        if(cursor != null){
            if(cursor.count > 0){
                cursor.moveToFirst()
                do{
                    val phoneNumber = cursor.getString(0)
                    listOfphoneNumber.add(phoneNumber)
                }while (cursor.moveToNext())
            }
        }
        return listOfphoneNumber
    }

    fun selectStudent(id: Int) : Student? {
        db = this.writableDatabase
        var query = "SELECT * FROM "+ TABLENAMESTUDENT+" WHERE "+ IDSSTUDENT+" = "+id
        val cursor  = db.rawQuery(query,null)
        var student: Student? = null
        if(cursor != null){
            if(cursor.count > 0){
                cursor.moveToFirst()
                do{

                    student =  Student(cursor.getInt(0),
                        cursor.getString(1),cursor.getString(2),cursor.getString(3),cursor.getInt(4),cursor.getInt(5))

                }while (cursor.moveToNext())
            }
        }
        return student
    }


    fun insertMessage( message: MessageDetail) : Long  {
        db = this.writableDatabase
        var cv  = ContentValues()

        cv.put(MESSAGEBODY,message.messageBody)
        cv.put(MESSAGECLASS,message.classeses)
        cv.put(MESSAGESTREAM,message.stream)
        cv.put(MESSAGEALLSCHOOL,message.allSchool)
        cv.put(MESSAGEINDIVIDUAL,message.individul)
        val result = db.insert(TABLENAMEMESSAGE,null,cv)
        Log.d(TAG," value inserted "+result)

        return result

    }

    fun selectListOfMessageDetail() : ArrayList<MessageDetail> {
        db = this.writableDatabase
        val query = "SELECT * FROM "+ TABLENAMEMESSAGE
        val cursor  = db.rawQuery(query,null)
        val listOfMessageDetail = arrayListOf<MessageDetail>()
        if(cursor != null){
            if(cursor.count > 0){
                cursor.moveToFirst()
                do{
                    val student = MessageDetail(cursor.getInt(0),cursor.getString(1),cursor.getString(2),cursor.getString(3),cursor.getString(4),cursor.getString(5))
                    listOfMessageDetail.add(student)
                }while (cursor.moveToNext())
            }
        }
        return listOfMessageDetail
    }

    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {
        TODO("Not yet implemented")
    }
}