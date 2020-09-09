package com.ieugene.kotlinlab.ui.adapter

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView

class DiffAdapter : ListAdapter<String, DiffAdapter.ItemHolder>(DiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemHolder {
        TODO("Not yet implemented")
    }

    override fun onBindViewHolder(holder: ItemHolder, position: Int) {
        TODO("Not yet implemented")
    }

    override fun submitList(list: MutableList<String>?) {
        super.submitList(list)
        list.apply {  }
        list.let {  }
        list.also {  }
        list.run {  }
    }

    class ItemHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    }

}