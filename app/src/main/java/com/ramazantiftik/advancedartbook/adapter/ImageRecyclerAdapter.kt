package com.ramazantiftik.advancedartbook.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.RequestManager
import com.ramazantiftik.advancedartbook.R
import com.ramazantiftik.advancedartbook.databinding.ImageRowBinding
import com.ramazantiftik.advancedartbook.model.Art
import javax.inject.Inject

class ImageRecyclerAdapter @Inject constructor(
    val glide: RequestManager
) : RecyclerView.Adapter<ImageRecyclerAdapter.ImageViewHolder>() {

    class ImageViewHolder(val imageRowBinding: ImageRowBinding) : ViewHolder(imageRowBinding.root)


    private var onItemClickListener : ((String) -> Unit) ?= null


    private val diffUtil=object: DiffUtil.ItemCallback<String>(){
        override fun areItemsTheSame(oldItem: String, newItem: String): Boolean {
            return oldItem==newItem //if old item is equal to new item return true
        }

        override fun areContentsTheSame(oldItem: String, newItem: String): Boolean {
            return oldItem==newItem
        }

    }


    private val recyclerListDiff=AsyncListDiffer(this,diffUtil)

    var images: List<String>
    get() = recyclerListDiff.currentList
    set(value) = recyclerListDiff.submitList(value)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        val recyclerImageRow=ImageRowBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ImageViewHolder(recyclerImageRow)
    }

    override fun getItemCount(): Int {
        return images.size
    }

    fun setOnItemClickListener(listener: (String) -> Unit){
        onItemClickListener=listener
    }

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {

        val url=images[position]
        glide.load(url).into(holder.imageRowBinding.imageViewImageApi)

        holder.itemView.setOnClickListener {
            onItemClickListener?.let {
                it(url)
            }
        }


    }

}