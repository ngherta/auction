package com.lot.mobile.presentation.features.profile.settings

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.lot.mobile.databinding.LayoutSettingItemBinding
import kotlin.math.log


object DiffCallback : DiffUtil.ItemCallback<SettingItemModel>() {
    override fun areItemsTheSame(oldItem: SettingItemModel, newItem: SettingItemModel): Boolean {
        return oldItem.name == newItem.name
    }

    override fun areContentsTheSame(oldItem: SettingItemModel, newItem: SettingItemModel): Boolean {
        return oldItem.name == newItem.name
    }
}

class SettingAdapter(
    private val onItemClick: (SettingItemModel) -> Unit
) : ListAdapter<SettingItemModel, SettingsViewHolder>(DiffCallback) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SettingsViewHolder {
        return SettingsViewHolder(
            LayoutSettingItemBinding.inflate(LayoutInflater.from(parent.context), parent, false),
            onItemClick
        )
    }

    override fun onBindViewHolder(holder: SettingsViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}

class SettingsViewHolder(
    private val binding: LayoutSettingItemBinding,
    private val onItemClick: (SettingItemModel) -> Unit
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(settingItem: SettingItemModel) {
        binding.switchCase.text = settingItem.name
        binding.switchCase.isChecked = settingItem.value
//        binding.switchCase.
        binding.root.setOnClickListener { onItemClick(settingItem) }

    }
}