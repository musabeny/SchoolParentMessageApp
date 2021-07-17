package com.example.ewula.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.beem.schoolmessageapp.R
import com.beem.schoolmessageapp.interfaces.ItemClickListerner
import com.beem.schoolmessageapp.model.Message
import com.beem.schoolmessageapp.model.MessageDetail
import com.beem.schoolmessageapp.model.Options


class MessageAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private  var listOfMessage:List<MessageDetail> = ArrayList()

    lateinit  var itemClickListerner: ItemClickListerner
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
       return MessageListView(
           LayoutInflater.from(parent.context).inflate(R.layout.message,parent,false)
       )
    }

    override fun getItemCount(): Int {

        return listOfMessage.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(holder){
            is MessageListView ->{
                holder.bind(listOfMessage.get(position))
            }
        }
    }

    fun submitListOfMessages(messages:List<MessageDetail>){

        listOfMessage = messages
    }

    fun onItemClickListerne(itemClick:ItemClickListerner?){
        if(itemClick != null){
          itemClickListerner = itemClick
        }
    }

    inner class MessageListView constructor(itemView : View) : RecyclerView.ViewHolder(itemView) , View.OnClickListener{
        var textMessage = itemView.findViewById<TextView>(R.id.textMessage)
        var sentToMessage =    itemView.findViewById<TextView>(R.id.sentToMessage)



        fun bind(message: MessageDetail){
            textMessage.text = message.messageBody
//            serviceImg.setImageDrawable(serviceList.imageUrl)
            if(message.allSchool.length > 3){
                sentToMessage.text = "sent to all parents"
            }else if(message.classeses.length > 3 && message.stream.length == 0 ){
                sentToMessage.text = "sent to   "+message.classeses
            }else if(message.stream.length > 3){
                sentToMessage.text = "sent to "+message.classeses+" , "+message.stream
            }else if(message.individul.length > 3){
                sentToMessage.text = "sent to  "+message.individul
            }else{
                sentToMessage.text = ""
            }

            itemView.setOnClickListener(this)
        }

        override fun onClick(view: View?) {
            itemClickListerner.onClick(view,adapterPosition,false)
        }
    }
}