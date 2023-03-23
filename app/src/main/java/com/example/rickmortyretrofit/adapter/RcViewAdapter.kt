package com.example.rickmortyretrofit.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.TintableImageSourceView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.rickmortyretrofit.R
import com.example.rickmortyretrofit.databinding.RcItemCopyBinding
import com.example.rickmortyretrofit.model.Results
import com.squareup.picasso.Picasso

class RcViewAdapter (
    private val clickOnItem: (Results) -> Unit,

        ):RecyclerView.Adapter<RcViewAdapter.MyHolder>() {
    private  var listItem : MutableList<Results> = mutableListOf()
    class MyHolder(
        private val binding: RcItemCopyBinding,
        private val clickOnItem: (Results) -> Unit,

        ) : RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("ResourceAsColor")
        fun bind(result:Results){

            setName(result.name)
            setImage(result.image)

            binding.personName.setOnClickListener {
                Toast.makeText(binding.root.context, "$position", Toast.LENGTH_SHORT).show()
            }


            binding.imageStateFavoriteButton.setOnClickListener {
                result.isLike = !result.isLike
                binding.imageStateFavoriteButton.isSelected = result.isLike

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
            clickOnItem

        )
    }

    override fun onBindViewHolder(holder: MyHolder, position: Int) {
        holder.bind(listItem[position])
    }

    override fun getItemCount(): Int {
        return listItem.size
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setList(list:List<Results>) {
        val personDiffUtil = PersonDiffUtil(
            oldList = listItem,
            newList = list
        )
        val diffResult = DiffUtil.calculateDiff(personDiffUtil)
        listItem.clear()
        listItem.addAll(list)
        diffResult.dispatchUpdatesTo(this)

    }
    @SuppressLint("NotifyDataSetChanged")
    fun addList(list:List<Results>){
        listItem.addAll(list)
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
        val newList: List<Results>,
        val oldList: List<Results>,
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