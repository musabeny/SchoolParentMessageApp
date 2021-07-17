package com.beem.schoolmessageapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.GridLayoutManager
import com.beem.schoolmessageapp.database.Database
import com.beem.schoolmessageapp.interfaces.ItemClickListerner
import com.beem.schoolmessageapp.model.Student
import com.beem.schoolmessageapp.util.Common
import com.beem.schoolmessageapp.util.Datas
import com.example.ewula.adapter.OptionAdapter
import com.example.ewula.adapter.StudentListAdapter
import com.example.ewula.adapter.TopSpacingItemDecoration
import kotlinx.android.synthetic.main.activity_stream.*
import kotlinx.android.synthetic.main.activity_student_list.*

class StudentListActivity : AppCompatActivity() {
    lateinit var studentListAdapter: StudentListAdapter
    lateinit var database: Database
    lateinit var listStudent:ArrayList<Student>
    lateinit var common: Common
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_student_list)
        initView()
    }

    fun initView(){
        //start of toolbar
//        toolbars = findViewById(R.id.classTools)
        val toolbar = studentToolslist.findViewById<Toolbar>(R.id.toolbares)
        toolbar.setTitle("Student")
        setSupportActionBar(toolbar)
        toolbar.setNavigationIcon(R.drawable.back_btn)
        toolbar.setNavigationOnClickListener(View.OnClickListener {
            finish()
        })
        database = Database(this)
        common = Common()
        initRecyclerView()
        onItemClicked()
        listStudent = database.selectListOfStudent()
        studentListAdapter.submitListOfMessages(listStudent)
        studentListAdapter.notifyDataSetChanged()

    }

    private fun initRecyclerView(){
        listStudentRecy.apply {
            layoutManager = GridLayoutManager(this@StudentListActivity,1)
            val topSopacingDecoration = TopSpacingItemDecoration(30)
            addItemDecoration(topSopacingDecoration)
            studentListAdapter = StudentListAdapter()

            adapter = studentListAdapter
        }

    }

    fun onItemClicked(){
        studentListAdapter.onItemClickListerne(object  : ItemClickListerner {
            override fun onClick(view: View?, position: Int, isLongClick: Boolean) {
               Datas.selectedStudent = listStudent.get(position)
                Datas.hintText = "Send to "+ Datas.selectedStudent.firstName+" "+ Datas.selectedStudent.lastName
                common.nextScreen(this@StudentListActivity,MessageActivity::class.java,0,Datas.selectedStudent.id,4)
            }

        })
    }
}