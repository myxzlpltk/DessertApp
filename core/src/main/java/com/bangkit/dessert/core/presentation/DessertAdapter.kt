package com.bangkit.dessert.core.presentation

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bangkit.dessert.core.databinding.ItemListDessertBinding
import com.bangkit.dessert.core.domain.model.DessertBrief
import com.bangkit.dessert.core.utils.load

class DessertAdapter(
    private val onClick: (DessertBrief) -> Unit
) : ListAdapter<DessertBrief, DessertAdapter.ViewHolder>(DessertDiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder = ViewHolder(
        ItemListDessertBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val dessert = getItem(position)

        with(holder.binding) {
            // Load image
            dessertImage.load(dessert.image)

            // Load data
            dessertName.text = dessert.name
            dessertCard.setOnClickListener { onClick.invoke(dessert) }
        }
    }

    inner class ViewHolder(
        val binding: ItemListDessertBinding
    ) : RecyclerView.ViewHolder(binding.root)

    companion object {
        object DessertDiffCallback : DiffUtil.ItemCallback<DessertBrief>() {
            override fun areItemsTheSame(oldItem: DessertBrief, newItem: DessertBrief): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: DessertBrief, newItem: DessertBrief): Boolean {
                return oldItem.id == newItem.id
            }
        }
    }
}