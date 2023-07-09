package com.ramazantiftik.advancedartbook.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.RequestManager
import com.ramazantiftik.advancedartbook.databinding.ArtsRecyclerRowBinding
import com.ramazantiftik.advancedartbook.model.Art
import javax.inject.Inject

class ArtRecyclerAdapter @Inject constructor(
    val glide: RequestManager
) : RecyclerView.Adapter<ArtRecyclerAdapter.ArtViewHolder>() {

    class ArtViewHolder(val artRecyclerRowBinding: ArtsRecyclerRowBinding) :RecyclerView.ViewHolder(artRecyclerRowBinding.root)


    private val diffUtil=object: DiffUtil.ItemCallback<Art>(){ //compare to arts of list , checking to update list
        override fun areItemsTheSame(oldItem: Art, newItem: Art): Boolean {
            return oldItem==newItem
        }

        override fun areContentsTheSame(oldItem: Art, newItem: Art): Boolean {
            return oldItem==newItem
        }

    }


    private val recyclerListDiffer=AsyncListDiffer(this,diffUtil)

    var arts: List<Art>
    get() = recyclerListDiffer.currentList
    set(value) = recyclerListDiffer.submitList(value)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArtViewHolder {
        val artsRecyclerRowBinding=ArtsRecyclerRowBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ArtViewHolder(artsRecyclerRowBinding)
    }

    override fun getItemCount(): Int {
        return arts.size
    }

    override fun onBindViewHolder(holder: ArtViewHolder, position: Int) {
        val art=arts[position]

        holder.artRecyclerRowBinding.artRowNameText.text="Name: ${art.artName}"
        holder.artRecyclerRowBinding.artRowArtistNameText.text="Artist Name: ${art.artistName}"
        holder.artRecyclerRowBinding.artRowYearText.text="Year: ${art.year}"
        glide.load(art.imageUrl)

    }

}