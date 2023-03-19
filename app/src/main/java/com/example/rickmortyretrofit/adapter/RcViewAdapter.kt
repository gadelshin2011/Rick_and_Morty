package com.example.rickmortyretrofit.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.rickmortyretrofit.R
import com.example.rickmortyretrofit.databinding.RcItemCopyBinding
import com.example.rickmortyretrofit.model.Location
import com.example.rickmortyretrofit.model.Result
import com.example.rickmortyretrofit.screens.startscreen.StartFragment
import com.squareup.picasso.Picasso

class RcViewAdapter (
    private val clickOnItem: (Result) -> Unit,
    private val clickOnLike: (Result) -> Unit
        ):RecyclerView.Adapter<RcViewAdapter.MyHolder>() {
    private var listItem : List<Result> = emptyList()
    class MyHolder(
        private val binding: RcItemCopyBinding,
        private val clickOnItem: (Result) -> Unit,
        private val clickOnLike: (Result) -> Unit
        ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(result:Result){

            setName(result.name)
            setImage(result.image)

            binding.personName.setOnClickListener {
                Toast.makeText(binding.root.context, "$position", Toast.LENGTH_SHORT).show()
            }


            binding.imageStateFavoriteButton.setOnClickListener {
                clickOnLike(result)
            }
        }
        private fun setName(name: String){
            binding.personName.text = name
        }

        private fun setImage(url: String) {
            Picasso.get().load(url).into(binding.photoPerson)
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyHolder {
        return MyHolder(
            RcItemCopyBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            ),
            clickOnItem,
            clickOnLike
        )
    }

    override fun onBindViewHolder(holder: MyHolder, position: Int) {
        holder.bind(listItem[position])
    }

    override fun getItemCount(): Int {
        return listItem.size
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setList(list:List<Result>){
        val personDiffUtil = PersonDiffUtil(
            oldList = listItem,
            newList = list
        )
        val diffResult = DiffUtil.calculateDiff(personDiffUtil)
        listItem = list
        diffResult.dispatchUpdatesTo(this)

    }
    @SuppressLint("NotifyDataSetChanged")
    fun addList(list:List<Result>){
        notifyDataSetChanged()
    }


    override fun onViewAttachedToWindow(holder: MyHolder) {
        super.onViewAttachedToWindow(holder)
        holder.itemView.setOnClickListener {
            clickOnItem(listItem[holder.adapterPosition])
        }
    }

    override fun onViewDetachedFromWindow(holder: MyHolder) {
        holder.itemView.setOnClickListener(null)
    }


    class PersonDiffUtil(
        val newList: List<Result>,
        val oldList: List<Result>,
    ): DiffUtil.Callback(){
        override fun getOldListSize(): Int {
            return oldList.size
        }

        override fun getNewListSize(): Int {
            return newList.size
        }

        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return newList[newItemPosition].id == oldList[oldItemPosition].id
        }

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return newList[newItemPosition] == oldList[oldItemPosition]
        }

    }

}