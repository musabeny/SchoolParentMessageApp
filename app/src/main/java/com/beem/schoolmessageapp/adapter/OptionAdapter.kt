package com.example.ewula.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.beem.schoolmessageapp.R
import com.beem.schoolmessageapp.interfaces.ItemClickListerner
import com.beem.schoolmessageapp.model.Options


class OptionAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private  var listOfOptions:List<Options> = ArrayList()

    lateinit  var itemClickListerner: ItemClickListerner
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
       return OptionsListView(
           LayoutInflater.from(parent.context).inflate(R.layout.options,parent,false)
       )
    }

    override fun getItemCount(): Int {

        return listOfOptions.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(holder){
            is OptionsListView ->{
                holder.bind(listOfOptions.get(position))
            }
        }
    }

    fun submitListOfOptions(option:List<Options>){

        listOfOptions = option
    }

    fun onItemClickListerne(itemClick:ItemClickListerner?){
        if(itemClick != null){
          itemClickListerner = itemClick
        }
    }

    inner class OptionsListView constructor(itemView : View) : RecyclerView.ViewHolder(itemView) , View.OnClickListener{
        var optionName = itemView.findViewById<TextView>(R.id.optionName)
        var optionDetail =    itemView.findViewById<TextView>(R.id.optionDetail)



        fun bind(options: Options){
            optionName.text = options.optionName
//            serviceImg.setImageDrawable(serviceList.imageUrl)
            optionDetail.text = options.optionDescription
            itemView.setOnClickListener(this)
        }

        override fun onClick(view: View?) {
            itemClickListerner.onClick(view,adapterPosition,false)
        }
    }
}