package com.lot.mobiledemo.presentation.features.auctions.list

import android.graphics.BitmapFactory
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.lot.mobiledemo.databinding.LayoutAuctionItemBinding
import com.lot.mobiledemo.presentation.features.auctions.AuctionItemModel
import java.net.URL


object DiffCallback : DiffUtil.ItemCallback<AuctionItemModel>() {
    override fun areItemsTheSame(oldItem: AuctionItemModel, newItem: AuctionItemModel): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: AuctionItemModel, newItem: AuctionItemModel): Boolean {
        return oldItem.id == newItem.id
    }
}

class AuctionsAdapter(
    private val onItemClick: (AuctionItemModel) -> Unit
) : ListAdapter<AuctionItemModel, AuctionsViewHolder>(DiffCallback) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AuctionsViewHolder {
        return AuctionsViewHolder(
            LayoutAuctionItemBinding.inflate(LayoutInflater.from(parent.context), parent, false),
            onItemClick
        )
    }

    override fun onBindViewHolder(holder: AuctionsViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}

class AuctionsViewHolder(
    private val binding: LayoutAuctionItemBinding,
    private val onItemClick: (AuctionItemModel) -> Unit
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(auctionItem: AuctionItemModel) {
        binding.title.text = auctionItem.title
        binding.root.setOnClickListener { onItemClick(auctionItem) }
    }
}
