package com.example.rickmortyretrofit.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.rickmortyretrofit.databinding.RcFavItemBinding
import com.example.rickmortyretrofit.model.Results
import com.squareup.picasso.Picasso

class RcViewFavAdapter:RecyclerView.Adapter<RcViewFavAdapter.MyFavHolder>() {
    var listFavItem : MutableList<Results> = mutableListOf()

    class MyFavHolder( private val binding: RcFavItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(result:Results){
            setName(result.name)
            setImage(result.image)
        }
        private fun setName(name: String){
            binding.personName.text = name
        }

        private fun setImage(url: String) {
            Picasso.get().load(url).into(binding.photoPerson)
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyFavHolder {
        return MyFavHolder(
            RcFavItemBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: MyFavHolder, position: Int) {
       holder.bind(listFavItem[position])
    }

    override fun getItemCount(): Int {
        return listFavItem.size
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setFavList(list:List<Results>){
        listFavItem.addAll(list)
        notifyDataSetChanged()

    }


}