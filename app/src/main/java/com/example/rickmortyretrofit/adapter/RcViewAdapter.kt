package com.example.rickmortyretrofit.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.rickmortyretrofit.APP
import com.example.rickmortyretrofit.R
import com.example.rickmortyretrofit.model.Location
import com.example.rickmortyretrofit.model.Result
import com.example.rickmortyretrofit.screens.startscreen.StartFragment
import com.squareup.picasso.Picasso

class RcViewAdapter:RecyclerView.Adapter<RcViewAdapter.MyHolder>(){
    private var listItem = ArrayList<Result>()
    class MyHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.rc_item_copy, parent, false)
        return MyHolder(view)
    }

    override fun onBindViewHolder(holder: MyHolder, position: Int) {
        holder.itemView.findViewById<TextView>(R.id.personName).text = listItem[position].name
        Picasso.get().load(listItem[position].image).into(holder.itemView.findViewById<ImageView>(R.id.photoPerson))

        holder.itemView.setOnClickListener {
            Toast.makeText(APP, "$position", Toast.LENGTH_SHORT).show()
        }


    }

    override fun getItemCount(): Int {
        return listItem.size
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setList(list:List<Result>){
        listItem = list as ArrayList<Result>
        notifyDataSetChanged()
    }
    @SuppressLint("NotifyDataSetChanged")
    fun addList(list:List<Result>){
        listItem.addAll(list)
        notifyDataSetChanged()
    }


    override fun onViewAttachedToWindow(holder: MyHolder) {
        super.onViewAttachedToWindow(holder)
        holder.itemView.setOnClickListener {
            StartFragment.clickNote(listItem[holder.adapterPosition])
        }
    }

    override fun onViewDetachedFromWindow(holder: MyHolder) {
        holder.itemView.setOnClickListener(null)
    }




}