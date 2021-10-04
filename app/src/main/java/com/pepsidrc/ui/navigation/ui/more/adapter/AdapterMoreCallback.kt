package com.pepsidrc.ui.navigation.ui.home.adapter

import androidx.recyclerview.widget.DiffUtil
import com.pepsidrc.model.home.Product
import com.pepsidrc.model.more.MoreItems


class AdapterMoreCallback : DiffUtil.ItemCallback<MoreItems>() {

    override fun areItemsTheSame(oldItem: MoreItems, newItem: MoreItems) = oldItem.id == newItem.id

    /* This method is called only if {@link #areItemsTheSame(T, T)} returns {@code true} for these items */
    override fun areContentsTheSame(oldItem: MoreItems, newItem: MoreItems): Boolean {
        return oldItem == newItem
    }
}