package com.beem.schoolmessageapp

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.beem.schoolmessageapp.database.Database
import com.beem.schoolmessageapp.interfaces.ItemClickListerner
import com.beem.schoolmessageapp.model.Options
import com.beem.schoolmessageapp.model.TAG
import com.beem.schoolmessageapp.util.Common
import com.beem.schoolmessageapp.util.Datas
import com.example.ewula.adapter.OptionAdapter
import com.example.ewula.adapter.TopSpacingItemDecoration
import java.util.*

class MessageOption : AppCompatActivity() {
    lateinit var optionList:RecyclerView
    lateinit var optionAdapter: OptionAdapter
    lateinit var database: Database
    lateinit var common: Common

    lateinit var listOfOptions:List<Options>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_message_option)


        initView()
        initBasicOperation()
        onItemClicked()

      
    }

    fun initView(){
        //start of toolbar
        val toolbar = findViewById<Toolbar>(R.id.toolbares)
        toolbar.setTitle("School Message")
        setSupportActionBar(toolbar)

        optionList = findViewById(R.id.optionList)
        initRecyclerView()
    }

    fun initBasicOperation(){
        //database init
        database = Database(this)
        //init common
        common = Common()
        if(database.countOptions().toInt() < 1){
            common.addOptionsToDb(database)
        }

        if(database.countCLasses().toInt() < 1){
            common.addClassesToDb(database)
        }

        if(database.countStream().toInt() < 1){
            common.addStreamToDb(database)
        }
        listOfOptions = database.selectListOfOptions()
        common.refleshList(listOfOptions,optionAdapter)

    }



    private fun initRecyclerView(){
        optionList.apply {
            layoutManager = GridLayoutManager(this@MessageOption,1)
            val topSopacingDecoration = TopSpacingItemDecoration(30)
            addItemDecoration(topSopacingDecoration)
            optionAdapter = OptionAdapter()
            adapter = optionAdapter
        }

    }

    fun onItemClicked(){
        optionAdapter.onItemClickListerne(object  : ItemClickListerner{
            override fun onClick(view: View?, position: Int, isLongClick: Boolean) {
//                Datas.allSchool = listOfOptions.get(position)
                when(position){
                    0 -> {
                        Datas.checkSender = listOfOptions.get(position)
                        common.nextScreen(this@MessageOption,MessageActivity::class.java,0,2000,1)
                        Datas.hintText = "Send to all Parents"
                    }
                    1 ->{
                        Datas.checkSender = listOfOptions.get(position)
                        common.nextScreen(this@MessageOption,ClassessActivity::class.java,0,1000,2)

                    }

                    2 -> {
                        Datas.checkSender = listOfOptions.get(position)
                        common.nextScreen(this@MessageOption,ClassessActivity::class.java,1,500,3)

                    }
                    3 -> {
                        Datas.checkSender = listOfOptions.get(position)
                        common.nextScreen(this@MessageOption,StudentListActivity::class.java,0,10000,4)

                    }

                    else -> Toast.makeText(this@MessageOption,"this option not found",Toast.LENGTH_SHORT).show()


                }

            }

        })
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = MenuInflater(this)
        inflater.inflate(R.menu.menu,menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId){
            R.id.addStudent ->{
                common.nextScreen(this,StudentActivity::class.java,0,0,0)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }


    }
}