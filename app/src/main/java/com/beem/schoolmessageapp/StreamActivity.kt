package com.beem.schoolmessageapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.GridLayoutManager
import com.beem.schoolmessageapp.database.Database
import com.beem.schoolmessageapp.interfaces.ItemClickListerner
import com.beem.schoolmessageapp.model.Options
import com.beem.schoolmessageapp.util.Common
import com.beem.schoolmessageapp.util.Datas
import com.example.ewula.adapter.OptionAdapter
import com.example.ewula.adapter.TopSpacingItemDecoration
import kotlinx.android.synthetic.main.activity_classess.*
import kotlinx.android.synthetic.main.activity_classess.classTools
import kotlinx.android.synthetic.main.activity_stream.*

class StreamActivity : AppCompatActivity() {

    lateinit var database: Database
    lateinit var common: Common
    lateinit var optionAdapter: OptionAdapter
    lateinit var listofOption:List<Options>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_stream)

        initView()

        initBasicOperation()
        onItemClicked()
    }


    fun initView(){
        //start of toolbar
//        toolbars = findViewById(R.id.classTools)
        val toolbar = streamTools.findViewById<Toolbar>(R.id.toolbares)
        toolbar.setTitle("Stream")
        setSupportActionBar(toolbar)
        toolbar.setNavigationIcon(R.drawable.back_btn)
        toolbar.setNavigationOnClickListener(View.OnClickListener {
            finish()
        })

        initRecyclerView()
    }

    fun initBasicOperation(){
        //database init
        database = Database(this)
        //init common
        common = Common()


        listofOption = database.selectListOfStream()
        common.refleshList(listofOption,optionAdapter)

    }

    private fun initRecyclerView(){
        streamList.apply {
            layoutManager = GridLayoutManager(this@StreamActivity,1)
            val topSopacingDecoration = TopSpacingItemDecoration(30)
            addItemDecoration(topSopacingDecoration)
            optionAdapter = OptionAdapter()
            adapter = optionAdapter
        }

    }

    fun onItemClicked(){
        optionAdapter.onItemClickListerne(object  : ItemClickListerner {
            override fun onClick(view: View?, position: Int, isLongClick: Boolean) {
                Datas.allStream = listofOption.get(position)
                when(position){
                    0 ->{
                        Datas.hintText = "Send to Grade "+Datas.classes+", Stream A parents  "

                        common.nextScreen(this@StreamActivity,MessageActivity::class.java,0,Datas.allStream.id,3)
                    }
                1 ->{
                    Datas.hintText =  "Send to Grade "+Datas.classes+", Stream B parents  "

                    common.nextScreen(this@StreamActivity,MessageActivity::class.java,0,Datas.allStream.id,3)
                }
                    2 ->{
                        Datas.hintText =  "Send to Grade "+Datas.classes+", Stream C parents  "
                        common.nextScreen(this@StreamActivity,MessageActivity::class.java,0,Datas.allStream.id,3)
                    }
                }
            }

        })
    }
}