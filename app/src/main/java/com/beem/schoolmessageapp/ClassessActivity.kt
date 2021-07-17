package com.beem.schoolmessageapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.beem.schoolmessageapp.database.Database
import com.beem.schoolmessageapp.interfaces.ItemClickListerner
import com.beem.schoolmessageapp.model.Options
import com.beem.schoolmessageapp.model.TAG
import com.beem.schoolmessageapp.model.nextScreenData
import com.beem.schoolmessageapp.util.Common
import com.beem.schoolmessageapp.util.Datas
import com.example.ewula.adapter.OptionAdapter
import com.example.ewula.adapter.TopSpacingItemDecoration
import kotlinx.android.synthetic.main.activity_classess.*

class ClassessActivity : AppCompatActivity() {
    lateinit var database: Database
    lateinit var common: Common
    lateinit var optionAdapter:OptionAdapter
    lateinit var options: Options
    lateinit var listofOption:List<Options>

    lateinit var classesList:RecyclerView
    var selectedChoice = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_classess)

        initView()

        initBasicOperation()
        onItemClicked()
    }

    fun initView(){
        //start of toolbar
//        toolbars = findViewById(R.id.classTools)
        val toolbar = classTools.findViewById<Toolbar>(R.id.toolbares)
        toolbar.setTitle("CLasses")
        setSupportActionBar(toolbar)
        toolbar.setNavigationIcon(R.drawable.back_btn)
        toolbar.setNavigationOnClickListener(View.OnClickListener {
            finish()
        })

        selectedChoice = intent.getIntExtra(nextScreenData,0)

        classesList = findViewById(R.id.clasessList)
        initRecyclerView()
    }

    fun initBasicOperation(){
        //database init
        database = Database(this)

        //init common
        common = Common()

        if(database.countCLasses().toInt() < 1){
            common.addClassesToDb(database)
        }
        listofOption = database.selectListOfClass()
        common.refleshList(listofOption,optionAdapter)

    }

    private fun initRecyclerView(){
        classesList.apply {
            layoutManager = GridLayoutManager(this@ClassessActivity,1)
            val topSopacingDecoration = TopSpacingItemDecoration(30)
            addItemDecoration(topSopacingDecoration)
            optionAdapter = OptionAdapter()
            adapter = optionAdapter
        }

    }

    fun onItemClicked(){
        optionAdapter.onItemClickListerne(object  : ItemClickListerner {
            override fun onClick(view: View?, position: Int, isLongClick: Boolean) {

                Datas.allClasses = listofOption.get(position)
                if(selectedChoice == 1){

                    when(position){
                        0 ->{
//                            Datas.option = listofOption.get(position)
                            Datas.classes = "One"
                            common.nextScreen(this@ClassessActivity,StreamActivity::class.java,0,Datas.allClasses.id,2)
                        }
                        1 -> {
//                            Datas.option = listofOption.get(position)
                            Datas.classes = "Two"
                            common.nextScreen(this@ClassessActivity,StreamActivity::class.java,0,Datas.allClasses.id,2)
                        }
                        2 -> {
                            Datas.classes = "Three"
//                            Datas.option = listofOption.get(position)
                            common.nextScreen(this@ClassessActivity,StreamActivity::class.java,0,Datas.allClasses.id,2)
                        }
                        3 ->{
//                            Datas.option = listofOption.get(position)
                            Datas.classes = "Four"
                            common.nextScreen(this@ClassessActivity,StreamActivity::class.java,0,Datas.allClasses.id,2)
                        }
                        4 -> {
//                            Datas.option = listofOption.get(position)
                            Datas.classes = "Five"
                            common.nextScreen(this@ClassessActivity,StreamActivity::class.java,0,Datas.allClasses.id,2)
                        }
                        5 -> {
//                            Datas.allClasses = listofOption.get(position)
                            Datas.classes = "Six"
                            common.nextScreen(this@ClassessActivity,StreamActivity::class.java,0,Datas.allClasses.id,2)
                        }
                        6 ->{
//                            Datas.allClasses = listofOption.get(position)
                            Datas.classes = "Seven"
                            common.nextScreen(this@ClassessActivity,StreamActivity::class.java,0,Datas.allClasses.id,2)
                        }
                        else -> {
                            Log.d(TAG,"That selection not found")
                        }
                    }

                }else{

                    when(position){
                        0 -> {
                            common.nextScreen(this@ClassessActivity,MessageActivity::class.java,0,Datas.allClasses.id,2)
                            Datas.hintText = "Send to  Grade One Parents"
                        }
                        1 -> {
                            common.nextScreen(this@ClassessActivity,MessageActivity::class.java,0,Datas.allClasses.id,2)
                            Datas.hintText = "Send to  Grade Two Parents"
                        }
                        2 -> {
                            common.nextScreen(this@ClassessActivity,MessageActivity::class.java,0,Datas.allClasses.id,2)
                            Datas.hintText = "Send to  Grade Three Parents"
                        }
                        3 -> {
                            common.nextScreen(this@ClassessActivity,MessageActivity::class.java,0,Datas.allClasses.id,2)
                            Datas.hintText = "Send to  Grade Four Parents"
                        }
                        4 ->{
                            Datas.hintText = "Send to  Grade Five Parents"
                            common.nextScreen(this@ClassessActivity,MessageActivity::class.java,0,Datas.allClasses.id,2)
                        }
                        5 -> {
                            Datas.hintText = "Send to  Grade Six Parents"
                            common.nextScreen(this@ClassessActivity,MessageActivity::class.java,0,Datas.allClasses.id,2)
                        }
                        6 ->{
                            Datas.hintText = "Send to  Grade Seven Parents"
                            common.nextScreen(this@ClassessActivity,MessageActivity::class.java,0,Datas.allClasses.id,2)
                        }
                    }

                }
            }

        })
    }
}