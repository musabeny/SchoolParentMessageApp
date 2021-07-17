package com.beem.schoolmessageapp

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.annotation.RequiresApi
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.beem.schoolmessageapp.database.Database
import com.beem.schoolmessageapp.interfaces.ItemClickListerner
import com.beem.schoolmessageapp.model.*
import com.beem.schoolmessageapp.repository.Repository
import com.beem.schoolmessageapp.util.Common
import com.beem.schoolmessageapp.util.Datas
import com.beem.schoolmessageapp.viewModel.BalanceViewModel
import com.beem.schoolmessageapp.viewModel.BalanceViewModelFactory
import com.example.ewula.adapter.MessageAdapter
import com.example.ewula.adapter.TopSpacingItemDecoration
import com.example.ewula.network.NetworkApi
import com.example.qms.loading.LoadingDialog
import kotlinx.android.synthetic.main.activity_classess.*
import kotlinx.android.synthetic.main.activity_message.*
import kotlinx.android.synthetic.main.activity_stream.*

class MessageActivity : AppCompatActivity() {
    lateinit var balanceViewModel: BalanceViewModel
    lateinit var database: Database
    lateinit var listOfStudent:List<Student>
    lateinit var listOfRecepient:ArrayList<Recipient>
    lateinit var networkApi: NetworkApi
    lateinit var loadingDialog: LoadingDialog
    lateinit var common: Common
    lateinit var messageAdapter: MessageAdapter
    lateinit var listOfMessage:ArrayList<MessageDetail>
    var selectedOption = 0
    var selectedChoice = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_message)



        initView()

//        checkBalance()
    }



    fun sendMessage(){

        val getMessage = sendMessageBox.text.toString()
        if(getMessage.isEmpty()){
            loadingDialog.warning("You must enter a message in a message box",this,"Message Required")
            return
        }

        if(selectedChoice == 1){
            listOfStudent = database.selectListOfStudent()
        }else if(selectedChoice == 4){

            var singleStudent = ArrayList<Student>()
            singleStudent.add(Datas.selectedStudent)

            listOfStudent = singleStudent

        }else {
            listOfStudent = database.selectedStudentForMessage(selectedOption,selectedChoice)

        }
        if(listOfStudent.size <= 0){
            loadingDialog.warning(" No student registered on the selected class",this,"Student not found")
            return
        }

        for( student in listOfStudent){
            listOfRecepient.add(Recipient(student.phoneNumber,student.id))
        }
        Log.d(TAG,"receiver "+listOfRecepient)

        val messagePost = MessagePost("0",getMessage,listOfRecepient,"","INFO")
        Log.d(TAG,"receiver after object "+messagePost.recipients.toString())
        networkApi.sendMessage(messagePost,loadingDialog,this,common ,database,messageAdapter,sendMessageBox)

    }


    fun initView(){
        //start of toolbar
//        toolbars = findViewById(R.id.classTools)
        val toolbar = messagesTools.findViewById<Toolbar>(R.id.toolbares)
        toolbar.setTitle("Messages")
        setSupportActionBar(toolbar)
        toolbar.setNavigationIcon(R.drawable.back_btn)
        toolbar.setNavigationOnClickListener(View.OnClickListener {
            finish()
        })

        selectedOption = intent.getIntExtra(selectedGroup,0)
        selectedChoice = intent.getIntExtra(groupChoice,0)
        database = Database(this)
        listOfRecepient = ArrayList<Recipient>()
        networkApi = NetworkApi()
        loadingDialog = LoadingDialog()
        common = Common()


        sendMessageBox.hint = Datas.hintText
        initRecyclerView()

        listOfMessage = database.selectListOfMessageDetail()
//        messageAdapter.submitListOfMessages(listOfMessage)
        common.refleshMessage(listOfMessage,messageAdapter)
        onItemClicked()
    }


    fun sendMessage(view: View){
        sendMessage()
    }

    private fun initRecyclerView(){
        messageParentsList.apply {
            layoutManager = GridLayoutManager(this@MessageActivity,1)
            val topSopacingDecoration = TopSpacingItemDecoration(30)
            addItemDecoration(topSopacingDecoration)
            messageAdapter = MessageAdapter()
            adapter = messageAdapter
        }

    }

    fun onItemClicked(){
        messageAdapter.onItemClickListerne(object  : ItemClickListerner {
            override fun onClick(view: View?, position: Int, isLongClick: Boolean) {

            }

        })
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = MenuInflater(this)
        inflater.inflate(R.menu.messages,menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId){
            R.id.balancesms ->{
                networkApi.checkBalance(this,loadingDialog,common)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }


    }
}