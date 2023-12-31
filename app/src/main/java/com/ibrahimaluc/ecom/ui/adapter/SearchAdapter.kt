package com.ibrahimaluc.ecom.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.ibrahimaluc.ecom.databinding.ItemSearchBinding
import com.ibrahimaluc.ecom.data.remote.model.productSearch.SearchResult

class SearchAdapter(private val clickControl: (Int) -> Unit) :
    RecyclerView.Adapter<SearchAdapter.SearchViewHolder>() {

    class SearchViewHolder(var binding: ItemSearchBinding) : ViewHolder(binding.root)

    private val diffCallBack = object : DiffUtil.ItemCallback<SearchResult>() {
        override fun areItemsTheSame(oldItem: SearchResult, newItem: SearchResult) =
            oldItem == newItem

        override fun areContentsTheSame(oldItem: SearchResult, newItem: SearchResult) =
            oldItem == newItem

    }
    private val listDiffer = AsyncListDiffer(this, diffCallBack)
    var searchList: List<SearchResult>
        get() = listDiffer.currentList
        set(value) = listDiffer.submitList(value)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchViewHolder {
        return SearchViewHolder(
            ItemSearchBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: SearchViewHolder, position: Int) {
        if (position in searchList.indices) {
            holder.binding.data = searchList[position]
            holder.binding.productCard.setOnClickListener {
                searchList[position].id?.let { productId -> clickControl(productId) }
            }
        }
    }

    override fun getItemCount(): Int {
        return searchList.size
    }
}