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
import com.beem.schoolmessageapp.model.Student


class StudentListAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private  var listOfStudent:List<Student> = ArrayList()

    lateinit  var itemClickListerner: ItemClickListerner
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
       return StudentListView(
           LayoutInflater.from(parent.context).inflate(R.layout.students_list,parent,false)
       )
    }

    override fun getItemCount(): Int {

        return listOfStudent.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(holder){
            is StudentListView ->{
                holder.bind(listOfStudent.get(position))
            }
        }
    }

    fun submitListOfMessages(students:List<Student>){

        listOfStudent = students
    }

    fun onItemClickListerne(itemClick:ItemClickListerner?){
        if(itemClick != null){
          itemClickListerner = itemClick
        }
    }

    inner class StudentListView constructor(itemView : View) : RecyclerView.ViewHolder(itemView) , View.OnClickListener{
        var firstname = itemView.findViewById<TextView>(R.id.firstnamelist)
        var lastname =    itemView.findViewById<TextView>(R.id.lastnamelist)



        fun bind(student: Student){
            firstname.text = student.firstName
            lastname.text = student.lastName

            itemView.setOnClickListener(this)
        }

        override fun onClick(view: View?) {
            itemClickListerner.onClick(view,adapterPosition,false)
        }
    }
}